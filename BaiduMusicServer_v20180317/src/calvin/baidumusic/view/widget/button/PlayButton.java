package calvin.baidumusic.view.widget.button;

import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import calvin.baidumusic.common.Constants;

/**
 * �Զ��岥��/��ͣ��ť
 * ʵ����������꾭���Ͱ��µ�����״̬
 * ʵ�ֲ���/��ͣͼ���л�����ʾ�ı��л�
 * @author Calvin
 *
 */
public class PlayButton extends MyButton {

	private static final long serialVersionUID = 1L;

	private String toolTipText;
	private String toolTipText2;
	
	private boolean isStart = false;
	
	public PlayButton(String baseIconPath, String overIconPath,
			String pressedIconPath, int width, int height) {
		super(baseIconPath, overIconPath, pressedIconPath, width, height);
	}
	
	/**
	 * 
	 * @param baseIconPath ����״̬ͼ��·��
	 * @param overIconPath ��꾭��ͼ��·��
	 * @param pressedIconPath  ����ͼ��·��
	 * @param width
	 * @param height
	 */
	public PlayButton(String toolTipText, String baseIconPath, String overIconPath,
			String pressedIconPath, int width, int height) {
		this(baseIconPath, overIconPath, pressedIconPath, width, height);
		this.toolTipText = toolTipText;
	}
	
	public PlayButton(String toolTipText, String toolTipText2, String baseIconPath, String overIconPath,
			String pressedIconPath, int width, int height) {

		this(toolTipText, baseIconPath, overIconPath, pressedIconPath, width, height);
		this.toolTipText2 = toolTipText2;
	}
	
	public void setPauseIcon() {
		isStart = true;
		this.setIcon(new ImageIcon(Constants.STOP_DEF));
		this.setRolloverIcon(new ImageIcon(Constants.STOP_ROLLOVER));
		this.setPressedIcon(new ImageIcon(Constants.STOP_PRESSED));
		updateUI();
	}
	
	public void setPlayIcon() {
		isStart = false;
		this.setIcon(new ImageIcon(Constants.START_DEF));
		this.setRolloverIcon(new ImageIcon(Constants.START_ROLLOVER));
		this.setPressedIcon(new ImageIcon(Constants.START_PRESSED));
		updateUI();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (isStart) {
			this.setToolTipText(toolTipText2);
			updateUI();
		} else {
			this.setToolTipText(toolTipText);
			updateUI();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {}
}
