package calvin.baidumusic.view.widget.slider;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * 
 * �Զ��� JSlider
 * 
 */
public class MySlider extends JSlider {

	private static final long serialVersionUID = 1L;
	

	public MySlider() {
		this.setUI(new SliderUI(this));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setFocusable(false);
		// �϶���͸��
		this.setOpaque(false);
	}

}

class SliderUI extends BasicSliderUI {
	public SliderUI(JSlider b) {
		super(b);
	}

	public void paintThumb(Graphics g) {

		// super.paintThumb(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); 
		g2d.setPaint(new Color(255, 255, 255));
		g2d.fillOval(thumbRect.x, thumbRect.y + 1, thumbRect.width, 
				thumbRect.height - 8);
	}

	public void paintTrack(Graphics g) {
		int cy, cw;
		Rectangle trackBounds = trackRect;
		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			Graphics2D g2 = (Graphics2D) g;
			cy = (trackBounds.height / 4) - 1;
			cw = trackBounds.width + 5;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.translate(trackBounds.x, trackBounds.y + cy);

			g2.setPaint(new GradientPaint(0, -cy + 5, new Color(166, 166, 166,175),
					0, cy, new Color(255, 255, 255), true));
			g2.fillRoundRect(0, -cy + 5, cw, cy, cy, cy);

			int trackLeft = 0;
			int trackRight = 0;
			trackRight = trackRect.width - 1;

			int middleOfThumb = 0;
			int fillLeft = 0;
			int fillRight = 0;

			middleOfThumb = thumbRect.x + (thumbRect.width / 2);
			middleOfThumb -= trackRect.x;

			if (!drawInverted()) {
				fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
				fillRight = middleOfThumb;
			} else {
				fillLeft = middleOfThumb;
				fillRight = !slider.isEnabled() ? trackRight - 1
						: trackRight - 2;
			}
			g2.setPaint(Color.white);
			g2.fillRoundRect(0, -cy + 5, fillRight - fillLeft, cy, cy, cy);

			g2.setPaint(slider.getBackground());
			// g2.fillRect(10, 10, cw, 5);

			// g2.setPaint(Color.WHITE);
			// g2.drawLine(0, cy, cw - 1, cy);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.translate(-trackBounds.x, -(trackBounds.y + cy));
		} else {
			super.paintTrack(g);
		}
	}
}
