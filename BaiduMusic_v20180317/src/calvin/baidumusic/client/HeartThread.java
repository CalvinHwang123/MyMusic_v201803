package calvin.baidumusic.client;

import java.io.IOException;
import java.net.Socket;

import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import net.sf.json.JSONObject;

/**
 * 客户端心跳包线程
 * 定时发送心跳包表示在线
 * @author Calvin
 *
 */
public class HeartThread implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	private Socket client = null;
	private ClientStream clientStream = null;
	
//	private int countDown = 5;// 模拟断线的倒计时变量
	
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
//				System.out.println("模拟心跳线程完毕");
//				try {
//					stop();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

}