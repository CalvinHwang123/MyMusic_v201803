package calvin.baidumusic.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.manager.PlayManager;
import calvin.baidumusic.util.SongInfoProcessor;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import net.sf.json.JSONObject;
/**
 * 客户端文件下载线程
 * 主要用于接收服务器发来的文件字节数据
 * @author Calvin
 *
 */
public class FileDownloadThread implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	private Socket client = null;
	private ClientStream clientStream = null;
	
//	private long length = 0L;
	private FileOutputStream fos = null;
	public static final String filePrex = "res/download/";
	private String fileName = null;
	
	public FileDownloadThread(Socket client) throws IOException {
		runFlag = true;
		this.client = client;
		clientStream = new ClientStream(client);
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
		clientStream.getDis().close();
		clientStream.getDos().close();
		client.close();
	}
	
	@Override
	public void run() {
		while (runFlag) {
			try {
				String msg = clientStream.receive();
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
//			length = jo.getLong("file_size");
			fileName = jo.getString("file_name");
			try {
				File directory = new File(filePrex);
				directory.mkdirs();
				File file = new File(directory, fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (fos != null) {
				JSONObject jo2 = new JSONObject();
				jo2.put("type", "download");
				jo2.put("reply", "OK");
				try {
					clientStream.send(jo2.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}

				// 接收字节
				byte[] buf = new byte[8192];
				int len = 0;
				try {
					while ((len = clientStream.read(buf)) != -1) {
						fos.write(buf, 0, len);
						fos.flush();
					}
					fos.close();
					stop();
					new MessageDialog(fileName.substring(0, fileName.lastIndexOf(".")) + " 下载完毕");
					
					if (PlayManager.isOnline) {
						// 下载完毕，从下载列表中播放
						PaneManager.getOnlineMusicPaneInstance().playFromDownloadList(fileName);
						PlayManager.isOnline = false;
					}
					
					// 下载的音乐信息写入数据库
					String filePath = filePrex + fileName;
					System.out.println("filepath = " + filePath);
					String name = fileName.substring(0, fileName.indexOf("-"));
					String singer = fileName.substring(fileName.indexOf("-") + 1, fileName.indexOf(".mp3"));
					DaoFactory.getSongDao().addSingleSong(new Song(0, name, singer, SongInfoProcessor.readSize(filePath), SongInfoProcessor.readDuration(filePath), filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	public ClientStream getClientStream() {
		return clientStream;
	}

	public void setClientStream(ClientStream clientStream) {
		this.clientStream = clientStream;
	}

}
