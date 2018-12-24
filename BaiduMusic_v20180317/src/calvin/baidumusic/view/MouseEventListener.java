package calvin.baidumusic.view;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;
/**
 * 鼠标事件处理监听器
 * @author Calvin
 *
 */
public class MouseEventListener implements MouseInputListener {

	Point origin;
	// 鼠标拖拽想要移动的目标组件
	Window window;

	public MouseEventListener(Window window) {
		this.window = window;
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
		window.setCursor(null);
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
		window.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		Point p = window.getLocation();
		window.setLocation(p.x + (e.getX() - origin.x), p.y
				+ (e.getY() - origin.y));
	}

	public void mouseMoved(MouseEvent e) {
	}

}