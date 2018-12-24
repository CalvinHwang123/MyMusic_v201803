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
	
	private boolean isPlaying = false;
	
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
		isPlaying = true;
		this.setIcon(new ImageIcon(Constants.PAUSE_DEF));
		this.setRolloverIcon(new ImageIcon(Constants.PAUSE_ROLLOVER));
		this.setPressedIcon(new ImageIcon(Constants.PAUSE_PRESSED));
		updateUI();
	}
	
	public void setPlayIcon() {
		isPlaying = false;
		this.setIcon(new ImageIcon(Constants.PLAY_DEF));
		this.setRolloverIcon(new ImageIcon(Constants.PLAY_ROLLOVER));
		this.setPressedIcon(new ImageIcon(Constants.PLAY_PRESSED));
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
		if (isPlaying) {
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
