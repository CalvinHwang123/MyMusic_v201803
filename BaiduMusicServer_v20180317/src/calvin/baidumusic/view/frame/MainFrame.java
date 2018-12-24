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
 * �ٶ����ַ�����������
 * @author Calvin
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MainFrame() {
		setTitle("�ٶ����ַ�����v1.0 by Calvin");
		setIconImage(new ImageIcon(Constants.ICO).getImage());
		// ������ǰ��������п�϶
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
	 * ����¼�����
	 * 
	 */
	class MouseEventListener implements MouseInputListener {

		Point origin;
		// �����ק��Ҫ�ƶ���Ŀ�����
		JFrame frame;

		public MouseEventListener(JFrame frame) {
			this.frame = frame;
			origin = new Point();
		}

		public void mouseClicked(MouseEvent e) {
		}

		/**
		 * ��¼��갴��ʱ�ĵ�
		 */
		public void mousePressed(MouseEvent e) {
			origin.x = e.getX();
			origin.y = e.getY();
		}

		public void mouseReleased(MouseEvent e) {
			frame.setCursor(null);
		}

		/**
		 * ����ƽ�������ʱ���������ͼ��Ϊ�ƶ�ͼ��
		 */
		public void mouseEntered(MouseEvent e) {
			// this.frame
			// .setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}

		/**
		 * ����Ƴ�������ʱ���������ͼ��ΪĬ��ָ��
		 */
		public void mouseExited(MouseEvent e) {
			// this.frame.setCursor(Cursor
			// .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		/**
		 * ����ڱ�������קʱ�����ô��ڵ�����λ�� �����µ�����λ�� = �ƶ�ǰ����λ��+�����ָ�뵱ǰ����-��갴��ʱָ���λ�ã�
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

