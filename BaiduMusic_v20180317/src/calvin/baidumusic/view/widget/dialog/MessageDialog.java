package calvin.baidumusic.view.widget.dialog;

import javax.swing.JDialog;
import javax.swing.JRootPane;

/**
 * ��Ϣ�Ի���
 * ��Ҫ������ʾ�û���Ϣ
 * @author Calvin
 *
 */
public class MessageDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public MessageDialog(String message) {
		super();
		// ȡ��JDialog����
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setModal(true);
		
		add(new MessagePane(this, message));
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
