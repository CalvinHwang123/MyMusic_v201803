package calvin.baidumusic.view.widget.button;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * �Զ��尴ť
 * ʵ����������꾭���Ͱ��µ�����״̬
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
		
		this.setBorderPainted(false);// �����ư�ť�߿�
		this.setFocusPainted(false);// �����ƽ���״̬
		this.setContentAreaFilled(false);
		this.setDoubleBuffered(false);// ��ʹ��˫�������
		this.setOpaque(false);// ���ð�ť͸��
		this.setFocusable(false);// ���ɻ�ý���
		// ��������ͼ��
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.addMouseListener(this);
	}
	
	/**
	 * 
	 * @param baseIconPath ����״̬ͼ��·��
	 * @param overIconPath ��꾭��ͼ��·��
	 * @param pressedIconPath  ����ͼ��·��
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
