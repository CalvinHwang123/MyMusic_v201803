package calvin.baidumusic.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import net.sf.json.JSONObject;
/**
 * 服务器文件传输线程
 * 主要用于处理客户端发来的文件传输请求信息
 * @author Calvin
 *
 */
public class FileTransferThread implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	private Socket client = null;
	private ServerStream serverStream = null;
	
	private String filePath = null;
	
	public FileTransferThread(Socket client) throws IOException {
		runFlag = true;
		this.client = client;
		serverStream = new ServerStream(client);
	}
	
	public void start() {
		th = new Thread(this);
		th.start();
	}
	
	public void resume() {
		runFlag = true;
	}
	
	public void suspend() {
		runFlag = false;
	}
	
	public void stop() throws IOException {
		runFlag = false;
		serverStream.getDis().close();
		serverStream.getDos().close();
		client.close();
	}
	
	@Override
	public void run() {
		while (runFlag) {
			try {
				String msg = serverStream.receive();
				handMsg(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handMsg(String msg) {
		JSONObject jo = JSONObject.fromObject(msg);
		String type = jo.getString("type");
		switch (type) {
		case "download":
			System.out.println("文件传输线程收到"+msg);
			if (msg.contains("path")) {
				filePath = jo.getString("path");
				transferFilePrepare(filePath);
			} else {
				if (jo.getString("reply").equals("OK")) {
					transferFile(filePath);
				}
			}
			break;
		}
		
	}

	private void transferFile(String filePath2) {
		try {
			FileInputStream fis = new FileInputStream(filePath2);
			int len = 0;
			byte[] buf = new byte[8192];
			while ((len = fis.read(buf)) != -1) {
				serverStream.write(buf, 0, len);
			}
			fis.close();
			stop();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void transferFilePrepare(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println(filePath + "文件不存在");
			return;
		}
//		long length = file.length();
		JSONObject jo = new JSONObject();
		jo.put("type", "download");
		jo.put("file_name", filePath.substring(filePath.lastIndexOf("\\") + 1));
//		jo.put("file_size", length);
		try {
			serverStream.send(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
