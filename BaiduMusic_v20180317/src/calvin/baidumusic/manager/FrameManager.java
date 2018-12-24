package calvin.baidumusic.manager;

import calvin.baidumusic.view.frame.MainFrame;

/**
 * 窗口管理类
 * 实现单例窗口类
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
