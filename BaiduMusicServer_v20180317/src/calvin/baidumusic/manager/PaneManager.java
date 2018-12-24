package calvin.baidumusic.manager;

import javax.swing.JFrame;

import calvin.baidumusic.view.pane.MainPane;

/**
 * ��������
 * ʵ�ֵ��������
 * @author Calvin
 *
 */
public class PaneManager {
	public static MainPane mainPane = null;
	
	private PaneManager() {}
	
	public static MainPane getMainPaneInstance(JFrame frame) {
		if (mainPane == null) {
			mainPane = new MainPane(frame);
		}
		return mainPane;
	}
}
