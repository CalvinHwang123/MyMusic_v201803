package calvin.baidumusic.server;

import java.io.IOException;
import java.util.Map.Entry;

import calvin.baidumusic.server.ClientThread;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;

/**
 * ����������������߳�
 * ��ʱ��ÿ���ͻ����̼߳�������1
 * ���������ﵽ3ʱ������Ϊ�ÿͻ����Ѿ�����
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
				System.out.println(entry.getKey() + "�ļ�����Ϊ " + entry.getValue().counter);
				if (entry.getValue().counter >= 3) {
					System.out.println(entry.getKey() + "�����ˣ�");
					// ���·�����������Ϣ
					PaneManager.getMainPaneInstance(FrameManager.getMainFrameInstance()).info.setText(entry.getKey() + "�����ˣ�");
					PaneManager.getMainPaneInstance(FrameManager.getMainFrameInstance()).currentOnline.setText(
							"����������" + --ClientThread.onlineSum);
					try {
						entry.getValue().stop();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// �Ƴ����û���Ϣ
					ClientThread.userMap.remove(entry.getKey());
					System.out.println("��ǰuserMap size " + ClientThread.userMap.size());
				}
			}
		}
	}

}