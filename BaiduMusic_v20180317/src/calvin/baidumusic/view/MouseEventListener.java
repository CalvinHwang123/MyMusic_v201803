package calvin.baidumusic.view;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;
/**
 * ����¼����������
 * @author Calvin
 *
 */
public class MouseEventListener implements MouseInputListener {

	Point origin;
	// �����ק��Ҫ�ƶ���Ŀ�����
	Window window;

	public MouseEventListener(Window window) {
		this.window = window;
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
		window.setCursor(null);
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
		window.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		Point p = window.getLocation();
		window.setLocation(p.x + (e.getX() - origin.x), p.y
				+ (e.getY() - origin.y));
	}

	public void mouseMoved(MouseEvent e) {
	}

}