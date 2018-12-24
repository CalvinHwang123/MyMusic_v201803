package calvin.baidumusic.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 文件传输服务器
 * 主要用于监听客户端文件传输功能的连接
 * @author Calvin
 *
 */
public class FileTransferServer implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	private final int port = 10001;
	private ServerSocket listener = null;
	private Socket client = null;
	
	public FileTransferServer() {
		runFlag = true;
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
		if (client != null)
			client.close();
		if (listener != null)
//			listener.close();
			listener = null;
	}
	
	@Override
	public void run() {
		try {
			listener = new ServerSocket(port);
			System.out.println("文件传输服务器开始监听客户端！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (runFlag) {
			try {
				if (listener != null) {
					client = listener.accept();
					System.out.println("文件传输服务器连接客户端成功！");
					new FileTransferThread(client).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
