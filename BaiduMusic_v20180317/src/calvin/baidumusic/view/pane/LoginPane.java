package calvin.baidumusic.view.pane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import calvin.baidumusic.common.Constants;
import calvin.baidumusic.util.TextureAtlas;
import calvin.baidumusic.view.MouseEventListener;
/**
 * 登录面板
 * 主要实现用户登录、立即注册和忘记密码
 * @author Calvin
 *
 */
public class LoginPane extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private RegisterLoginPane rlp = null;

	public LoginPane(boolean login, boolean forget) {
		super();
		// 取消JDialog标题
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setModal(true);
		new RegisterLoginPane(this, login, forget);
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		rlp = new RegisterLoginPane(this, login, forget);
		add(rlp);
		
		RightPane adPane = new RightPane(this);
		add(adPane);
		
		pack();
		setLocationRelativeTo(null);
		
		MouseEventListener listener = new MouseEventListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
		setVisible(true);
	}

	public RegisterLoginPane getRlp() {
		return rlp;
	}

	public void setRlp(RegisterLoginPane rlp) {
		this.rlp = rlp;
	}
	
}



class RightPane extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 215;
	private final int HEIGHT = 295;
	
	private Graphics g;
	private BufferedImage closeImage = null;
	private Point closePoint = null;
	private Rectangle closeRect = null;
	
	private LoginPane parent = null;
	
	public RightPane(LoginPane parent) {
		this.parent = parent;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		
		setBackground(new Color(248, 248, 248));
		JLabel ad = new JLabel();
		ad.setIcon(new ImageIcon(Constants.AD));
		ad.setBounds(10, 100, 198, 104);
		add(ad);
		
		closePoint = new Point(183, 0);
		closeRect = new Rectangle((int) closePoint.getX(), (int) closePoint.getY(), 32, 35);
		closeImage = new TextureAtlas(Constants.LOGIN_CLOSE).cut(0, 0, 32, 35);
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.g = g;
		g.drawImage(closeImage, (int) closePoint.getX(), (int) closePoint.getY(), null);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int eX = e.getX();
		int eY = e.getY();
		if (closeRect.contains(eX, eY)) {
			closeImage = new TextureAtlas(Constants.LOGIN_CLOSE).cut(32, 0, 32, 35);
			setNormalOrHighLightImage(closeImage, 32);
		} else if (!closeRect.contains(eX, eY)) {
			closeImage = new TextureAtlas(Constants.LOGIN_CLOSE).cut(0, 0, 32, 35);
			setNormalOrHighLightImage(closeImage, 0);
		}
	}
	
	private void setNormalOrHighLightImage(BufferedImage bufImage, int startX) {
		g.drawImage(bufImage, startX, 0, null);
		repaint();
	 }

	@Override
	public void mouseClicked(MouseEvent e) {
		int eX = e.getX();
		int eY = e.getY();
		if (closeRect.contains(eX, eY)) {
			parent.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		int eX = e.getX();
		int eY = e.getY();
		if (closeRect.contains(eX, eY)) {
			closeImage = new TextureAtlas(Constants.LOGIN_CLOSE).cut(32, 0, 32, 35);
			setNormalOrHighLightImage(closeImage, 32);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		int eX = e.getX();
		int eY = e.getY();
		if (!closeRect.contains(eX, eY)) {
			closeImage = new TextureAtlas(Constants.LOGIN_CLOSE).cut(0, 0, 32, 35);
			setNormalOrHighLightImage(closeImage, 32);
		}
	}
}