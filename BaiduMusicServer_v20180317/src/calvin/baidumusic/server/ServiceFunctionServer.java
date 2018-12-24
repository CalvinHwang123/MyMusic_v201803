package calvin.baidumusic.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ҵ���ܷ�����
 * ��Ҫ���ڼ����ͻ�����ͨҵ���ܵ�����
 * @author Calvin
 *
 */
public class ServiceFunctionServer implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	private final int port = 10000;
	private ServerSocket listener = null;
	private Socket client = null;
	
	public ServiceFunctionServer() {
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
			System.out.println("ҵ���ܷ�������ʼ�����ͻ��ˣ�");
			// ������������߳�
			new HeartCheckThread().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (runFlag) {
			try {
				if (listener != null) {
					client = listener.accept();
					System.out.println("ҵ���ܷ��������ӿͻ��˳ɹ���");
					new ClientThread(client).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
