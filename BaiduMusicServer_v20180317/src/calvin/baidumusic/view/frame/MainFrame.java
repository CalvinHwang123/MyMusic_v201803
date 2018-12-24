package calvin.baidumusic.view.frame;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import calvin.baidumusic.common.Constants;
import calvin.baidumusic.manager.PaneManager;
/**
 * 百度音乐服务器主界面
 * @author Calvin
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MainFrame() {
		setTitle("百度音乐服务器v1.0 by Calvin");
		setIconImage(new ImageIcon(Constants.ICO).getImage());
		// 代码置前，否则会有空隙
		setResizable(false);
		setUndecorated(true);

		add("Center", PaneManager.getMainPaneInstance(this));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		MouseEventListener listener = new MouseEventListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
	
	/**
	 * 鼠标事件处理
	 * 
	 */
	class MouseEventListener implements MouseInputListener {

		Point origin;
		// 鼠标拖拽想要移动的目标组件
		JFrame frame;

		public MouseEventListener(JFrame frame) {
			this.frame = frame;
			origin = new Point();
		}

		public void mouseClicked(MouseEvent e) {
		}

		/**
		 * 记录鼠标按下时的点
		 */
		public void mousePressed(MouseEvent e) {
			origin.x = e.getX();
			origin.y = e.getY();
		}

		public void mouseReleased(MouseEvent e) {
			frame.setCursor(null);
		}

		/**
		 * 鼠标移进标题栏时，设置鼠标图标为移动图标
		 */
		public void mouseEntered(MouseEvent e) {
			// this.frame
			// .setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}

		/**
		 * 鼠标移出标题栏时，设置鼠标图标为默认指针
		 */
		public void mouseExited(MouseEvent e) {
			// this.frame.setCursor(Cursor
			// .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		/**
		 * 鼠标在标题栏拖拽时，设置窗口的坐标位置 窗口新的坐标位置 = 移动前坐标位置+（鼠标指针当前坐标-鼠标按下时指针的位置）
		 */
		public void mouseDragged(MouseEvent e) {
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			Point p = frame.getLocation();
			frame.setLocation(p.x + (e.getX() - origin.x), p.y
					+ (e.getY() - origin.y));
		}

		public void mouseMoved(MouseEvent e) {
		}

	}
	
}

