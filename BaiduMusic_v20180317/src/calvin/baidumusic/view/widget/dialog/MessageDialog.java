package calvin.baidumusic.view.widget.dialog;

import javax.swing.JDialog;
import javax.swing.JRootPane;

/**
 * 消息对话框
 * 主要用于提示用户信息
 * @author Calvin
 *
 */
public class MessageDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public MessageDialog(String message) {
		super();
		// 取消JDialog标题
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setModal(true);
		
		add(new MessagePane(this, message));
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
