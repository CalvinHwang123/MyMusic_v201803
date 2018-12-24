package calvin.baidumusic.view.widget.textfield;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
/**
 * 带有提示文本的密码框
 * @author Calvin
 *
 */
public class MyPasswordField extends JPasswordField implements FocusListener {

	private static final long serialVersionUID = 1L;
	
	private String hintText;

	public MyPasswordField(String hintText) {
		super(hintText);
		this.hintText = hintText;
		addFocusListener(this);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		 setText("");
	}

	@Override
	public void focusLost(FocusEvent e) {
//		setEchoChar('0');
		setText(hintText);
	}
}
