package calvin.baidumusic.view.widget.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import calvin.baidumusic.view.MouseEventListener;

public class MessagePane extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int width = 200;
	private final int height = 100;
	
	public MessagePane(MessageDialog parent, String message) {
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(233, 233, 233));
		setLayout(null);
		JLabel info = new JLabel(message);
		info.setBounds(0, 0, width, 60);
		info.setForeground(new Color(63, 165, 255));
		info.setHorizontalAlignment(JLabel.CENTER);
		add(info);
		
		JButton confirm = new JButton("È·¶¨");
		confirm.setBounds(75, 60, 50, 25);
		confirm.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		confirm.setBackground(new Color(63, 165, 255));
		confirm.setForeground(Color.WHITE);
		confirm.setBorder(BorderFactory.createEmptyBorder());
		confirm.setFocusable(false);
		add(confirm);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
			}
		});
		
		MouseEventListener listener = new MouseEventListener(parent);
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
}
