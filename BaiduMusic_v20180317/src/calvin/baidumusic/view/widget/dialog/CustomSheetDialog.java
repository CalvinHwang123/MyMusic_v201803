package calvin.baidumusic.view.widget.dialog;

import javax.swing.JDialog;
import javax.swing.JRootPane;

/**
 * �Զ���赥�Ի���
 * ��Ҫ���ڱ༭�赥����
 * @author Calvin
 *
 */
public class CustomSheetDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public static final int ADD = 0;
	public static final int RENAME = 1;
	
	public CustomSheetDialog(int mode) {
		super();
		// ȡ��JDialog����
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setModal(true);
		
		add(new CustomSheetPane(this, mode));
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
