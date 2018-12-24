package calvin.baidumusic.view.widget.dialog;

import javax.swing.JDialog;
import javax.swing.JRootPane;

/**
 * 自定义歌单对话框
 * 主要用于编辑歌单名称
 * @author Calvin
 *
 */
public class CustomSheetDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public static final int ADD = 0;
	public static final int RENAME = 1;
	
	public CustomSheetDialog(int mode) {
		super();
		// 取消JDialog标题
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setModal(true);
		
		add(new CustomSheetPane(this, mode));
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
