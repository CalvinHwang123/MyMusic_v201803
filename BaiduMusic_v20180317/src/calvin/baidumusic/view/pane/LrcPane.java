package calvin.baidumusic.view.pane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import calvin.baidumusic.view.widget.scrolllist.MyLrcList;
/**
 * 歌词面板
 * 主要显示滚动歌词、歌手写真
 * @author Calvin
 *
 */
public class LrcPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 825;
	private final int HEIGHT = 492;
	
	public MyLrcList lrcList = null;
	
	public LrcPane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(new Color(128, 255, 0, 0));
		setBorder(BorderFactory.createEmptyBorder());
		lrcList = new MyLrcList(this, WIDTH, HEIGHT);
		setViewportView(lrcList);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon original = new ImageIcon("res/lrc/zbc1_01.jpg");
		ImageIcon target = new ImageIcon(original.getImage()
				.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
		g.drawImage(target.getImage(), 0, 0, this);
	}
}
