package calvin.baidumusic.view.frame;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import calvin.baidumusic.common.Constants;
import calvin.baidumusic.manager.PaneManager;
/**
 * 百度音乐主界面
 * @author Calvin
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MainFrame() {
		setTitle("百度音乐v1.0 by Calvin");
		setIconImage(new ImageIcon(Constants.ICO).getImage());
		// 代码置前，否则会有空隙
		setResizable(false);
		setUndecorated(true);

		setBackground(Color.BLACK);
		add("North", PaneManager.getHeadPaneInstance(this));
		add("West", PaneManager.getListPaneInstance());
		add("Center", PaneManager.getMyCardPaneInstance());
		add("South", PaneManager.getMusicOperatePaneInstance());
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

