package calvin.baidumusic.view.widget.button;

import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import calvin.baidumusic.common.Constants;

/**
 * 自定义播放/暂停按钮
 * 实现正常、鼠标经过和按下的三种状态
 * 实现播放/暂停图标切换，提示文本切换
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
	 * @param baseIconPath 正常状态图标路径
	 * @param overIconPath 鼠标经过图标路径
	 * @param pressedIconPath  按下图标路径
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
