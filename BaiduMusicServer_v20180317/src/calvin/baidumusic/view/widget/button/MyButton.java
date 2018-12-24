package calvin.baidumusic.view.widget.button;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * 自定义按钮
 * 实现正常、鼠标经过和按下的三种状态
 * @author Calvin
 *
 */
public class MyButton extends JButton implements MouseListener {

	private static final long serialVersionUID = 1L;

	private String toolTipText;
	
	public MyButton(String baseIconPath, String overIconPath,
			String pressedIconPath, int width, int height) {
		
		this.setIcon(new ImageIcon(baseIconPath));
		this.setRolloverIcon(new ImageIcon(overIconPath));
		this.setPressedIcon(new ImageIcon(pressedIconPath));
		this.setSize(width, height);
		
		this.setBorderPainted(false);// 不绘制按钮边框
		this.setFocusPainted(false);// 不绘制焦点状态
		this.setContentAreaFilled(false);
		this.setDoubleBuffered(false);// 不使用双缓冲机制
		this.setOpaque(false);// 设置按钮透明
		this.setFocusable(false);// 不可获得焦点
		// 设置鼠标的图标
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.addMouseListener(this);
	}
	
	/**
	 * 
	 * @param baseIconPath 正常状态图标路径
	 * @param overIconPath 鼠标经过图标路径
	 * @param pressedIconPath  按下图标路径
	 * @param width
	 * @param height
	 */
	public MyButton(String toolTipText, String baseIconPath, String overIconPath,
			String pressedIconPath, int width, int height) {
		this(baseIconPath, overIconPath, pressedIconPath, width, height);
		this.toolTipText = toolTipText;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setToolTipText(toolTipText);
		updateUI();
	}

	@Override
	public void mouseExited(MouseEvent e) {}
}
