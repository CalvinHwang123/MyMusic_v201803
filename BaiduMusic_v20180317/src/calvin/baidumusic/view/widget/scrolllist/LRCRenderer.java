package calvin.baidumusic.view.widget.scrolllist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
/**
 * @author Calvin
 *
 */
public class LRCRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	public LRCRenderer() {
		super();
		setOpaque(false);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 26));
		setForeground(new Color(255, 255, 255, 180));
		if(value != null){
			setText(value.toString());
		}
		setHorizontalAlignment(JLabel.CENTER);
		if (index == 10) {
			setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 30));
			setForeground(Color.YELLOW);
		}
		return this;
	}

}
