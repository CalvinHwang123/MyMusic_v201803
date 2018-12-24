package calvin.baidumusic.view.widget.textfield;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
/**
 * 带有提示文本的圆角搜索框
 * @author Calvin
 *
 */
public class MySearchBar extends JTextField implements FocusListener {

	private static final long serialVersionUID = 1L;
	
	private String hintText;

	public MySearchBar(String hintText) {
		super(hintText);
		this.hintText = hintText;
		setFocusable(false);
		addFocusListener(this);
	}
	
	@Override 
	protected void paintComponent(Graphics g) {
        if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
            g2.dispose();
        }
        super.paintComponent(g);
    }
	
    @Override 
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
        setBorder(new RoundedCornerBorder());
    }

	@Override
	public void focusGained(FocusEvent e) {
		 setText("");
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (!getText().equals(hintText)
				&& !getText().equals("")) {
			return;
		}
		setText(hintText);
	}
}

class RoundedCornerBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	private static final Color ALPHA_ZERO = new Color(0x0, true);
    @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape border = getBorderShape(x, y, width - 1, height - 1);

//         Container parent = c.getParent();
//         if (Objects.nonNull(parent)) {
//             //g2.setPaint(parent.getBackground());
//             g2.setPaint(new Color(0x0, true));
//             Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
//             corner.subtract(new Area(border));
//             g2.fill(corner);
//         }

        g2.setPaint(ALPHA_ZERO);
        //Area corner = new Area(border.getBounds2D());
        Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
        corner.subtract(new Area(border));
        g2.fill(corner);

        g2.setPaint(Color.GRAY);
        g2.draw(border);
        g2.dispose();
    }
    public Shape getBorderShape(int x, int y, int w, int h) {
        int r = h; //h / 2;
        return new RoundRectangle2D.Double(x, y, w, h, r, r);
    }
    @Override public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }
    @Override public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, 8, 4, 8);
        return insets;
    }
}
