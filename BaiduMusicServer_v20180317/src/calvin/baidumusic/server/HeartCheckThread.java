package calvin.baidumusic.server;

import java.io.IOException;
import java.util.Map.Entry;

import calvin.baidumusic.server.ClientThread;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;

/**
 * 服务器心跳包检测线程
 * 定时对每个客户端线程计数器加1
 * 当计数器达到3时，可认为该客户端已经断线
 * @author Calvin
 *
 */
public class HeartCheckThread implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	public HeartCheckThread() throws IOException {
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
	}
	
	@Override
	public void run() {
		while (runFlag) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Entry<String, ClientThread> entry: ClientThread.userMap.entrySet()) {
				entry.getValue().counter++;
				System.out.println(entry.getKey() + "的计数器为 " + entry.getValue().counter);
				if (entry.getValue().counter >= 3) {
					System.out.println(entry.getKey() + "离线了！");
					// 更新服务器界面信息
					PaneManager.getMainPaneInstance(FrameManager.getMainFrameInstance()).info.setText(entry.getKey() + "离线了！");
					PaneManager.getMainPaneInstance(FrameManager.getMainFrameInstance()).currentOnline.setText(
							"在线人数：" + --ClientThread.onlineSum);
					try {
						entry.getValue().stop();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 移除该用户信息
					ClientThread.userMap.remove(entry.getKey());
					System.out.println("当前userMap size " + ClientThread.userMap.size());
				}
			}
		}
	}

}