package calvin.baidumusic.manager;

import calvin.baidumusic.view.frame.MainFrame;

/**
 * ���ڹ�����
 * ʵ�ֵ���������
 * @author Calvin
 *
 */
public class FrameManager {
	public static MainFrame mainFrame = null;
	
	private FrameManager() {}
	
	public static MainFrame getMainFrameInstance() {
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}
}
