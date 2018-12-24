package calvin.baidumusic.client;

import java.io.IOException;
import java.net.Socket;

import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import net.sf.json.JSONObject;

/**
 * �ͻ����������߳�
 * ��ʱ������������ʾ����
 * @author Calvin
 *
 */
public class HeartThread implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	private Socket client = null;
	private ClientStream clientStream = null;
	
//	private int countDown = 5;// ģ����ߵĵ���ʱ����
	
	public HeartThread(Socket client) throws IOException {
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
//			if (countDown > 0) {
				JSONObject jo = new JSONObject();
				jo.put("type", "heart");
				jo.put("user_name", PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).user.getuName());
				try {
					clientStream.send(jo.toString());
					Thread.sleep(5000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				countDown--;
//			} else {
//				System.out.println("ģ�������߳����");
//				try {
//					stop();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

}