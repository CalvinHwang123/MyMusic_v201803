package calvin.baidumusic.view.frame;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import calvin.baidumusic.common.Constants;
import calvin.baidumusic.manager.PaneManager;
/**
 * �ٶ�����������
 * @author Calvin
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MainFrame() {
		setTitle("�ٶ�����v1.0 by Calvin");
		setIconImage(new ImageIcon(Constants.ICO).getImage());
		// ������ǰ��������п�϶
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

