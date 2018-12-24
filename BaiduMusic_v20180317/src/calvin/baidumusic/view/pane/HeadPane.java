package calvin.baidumusic.view.pane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import calvin.baidumusic.bean.User;
import calvin.baidumusic.common.Constants;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.test.MyTest;
import calvin.baidumusic.view.MouseEventListener;
import calvin.baidumusic.view.widget.button.MyButton;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import calvin.baidumusic.view.widget.textfield.MySearchBar;
import net.sf.json.JSONObject;
/**
 * 头部面板
 * 提供用户头像、搜索栏、我的音乐和在线音乐切换、最小化和关闭等操作
 * @author Calvin
 *
 */
public class HeadPane extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 1000;
	private final int HEIGHT = 80;

	private Image LOGO = new ImageIcon(Constants.HEAD_LOGO).getImage();
	private Image MAIN_BG = new ImageIcon(Constants.HEAD_MAIN).getImage();
	
	private JFrame frame  = null;
	
	private MyButton minBtn  = null;
	private MyButton closeBtn  = null;
	
	public MyButton loginAvatar  = null;
	public boolean isLogin = false;
	public boolean socketClose = false;
	
	private MySearchBar searchBar = null;
	private MyButton searchBtn = null;
	
	private MyButton myMusicBtn = null;
	
	public JLabel userLabel = null;
	public JLabel exitLogin = null;
	
	public static LoginPane loginPane = null;
	
	public User user = null;
	
	public HeadPane(JFrame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		
		myMusicBtn = new MyButton(null, Constants.MY_MUSIC_TAB, Constants.MY_MUSIC_TAB2, Constants.MY_MUSIC_TAB3, 64, 64);
		myMusicBtn.setLocation(450, 8);
		add(myMusicBtn);
		
		minBtn = new MyButton("最小化", Constants.MIN_DEF, Constants.MIN_ROLLOVER, Constants.MIN_PRESSED, 
				30, 30);
		minBtn.setLocation(940, 0);
		add(minBtn);
		closeBtn = new MyButton("关闭", Constants.CLOSE_DEF, Constants.CLOSE_ROLLOVER, Constants.CLOSE_PRESSED, 
				30, 30);
		closeBtn.setLocation(970, 0);
		add(closeBtn);
		
		loginAvatar = new MyButton(null, Constants.DEFAULT_AVATAR, Constants.DEFAULT_AVATAR, Constants.DEFAULT_AVATAR, 
				35, 35);
		loginAvatar.setLocation(20, 35);
		add(loginAvatar);
		
		userLabel = new JLabel("未登录");
		userLabel.setBounds(72, 42, 100, 25);
		userLabel.setForeground(new Color(233, 231, 239));
		add(userLabel);
		
		exitLogin = new JLabel("退出登录");
		exitLogin.setBounds(165, 42, 60, 25);
		exitLogin.setForeground(new Color(233, 231, 239));
		add(exitLogin);
		exitLogin.setVisible(false);
		exitLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isLogin) {
					// 发送下线请求
					JSONObject jo = new JSONObject();
					jo.put("type", "exit");
					jo.put("username", PaneManager.getHeadPaneInstance(
							FrameManager.getMainFrameInstance()).user.getuName());
					try {
						MyTest.ct.getClientStream().send(jo.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					changeStatus();
				}
			}

			private void changeStatus() {
				isLogin = false;
				loginAvatar.setIcon(new ImageIcon(Constants.DEFAULT_AVATAR));
				loginAvatar.setRolloverIcon((new ImageIcon(Constants.DEFAULT_AVATAR)));
				loginAvatar.setPressedIcon((new ImageIcon(Constants.DEFAULT_AVATAR)));
				userLabel.setText("未登录");
				exitLogin.setVisible(false);
				
				try {
					MyTest.ct.ht.stop();
				} catch (IOException e) {
					e.printStackTrace();
				}
				socketClose = true;
			}
		});
		
		searchBar = new MySearchBar("本地、在线都能搜哦");
		searchBar.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		searchBar.setForeground(new Color(106, 106, 106));
		searchBar.setBounds(250, 42, 152, 25);
		add(searchBar);
		
		searchBtn = new MyButton("搜索", Constants.SEARCH, Constants.SEARCH2, Constants.SEARCH3, 18, 18);
		searchBtn.setLocation(130, 3);
		searchBar.setLayout(null);
		searchBar.add(searchBtn);
		
		searchBar.addMouseListener(this);
		searchBtn.addMouseListener(this);
		minBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		loginAvatar.addActionListener(this);
		MouseEventListener listener = new MouseEventListener(this.frame);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(MAIN_BG, 0, 0, null);
		g.drawImage(LOGO, 8, 8, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == minBtn) {
			frame.setExtendedState(JFrame.ICONIFIED);
		} else if (obj == closeBtn) {
			if (JOptionPane.showConfirmDialog(null, "是否退出？", "温馨提示", JOptionPane.YES_NO_OPTION) ==  JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		} else if (obj == loginAvatar) {
			if (isLogin) {
				new MessageDialog("用户已登录");
				return;
			}
			loginPane = new LoginPane(true, false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == searchBtn) {
			String keyword = searchBar.getText();
			if (!keyword.equals("")) {
				// 本地搜索结果
				PaneManager.getSearchResultPaneInstance().list = DaoFactory.getSongDao().listAllSongBySearch(keyword);
				
				// 只能本地搜索
				if (MyTest.ct == null) {
					PaneManager.getSearchResultPaneInstance().songTable.updateCurrentPage(
							PaneManager.getSearchResultPaneInstance().list, 1);
					PaneManager.getSearchResultPaneInstance().songSum.setText("搜索结果 " + 
							PaneManager.getSearchResultPaneInstance().list.size() + "首");
					PaneManager.getMyCardPaneInstance().getCardLayout().show(PaneManager.getMyCardPaneInstance(), MyCardPane.SEARCHRESULTPANE);
				} else {
					// 发送搜索请求
					JSONObject jo = new JSONObject();
					jo.put("type", "music");
					jo.put("search_str", keyword);
					try {
						MyTest.ct.getClientStream().send(jo.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == searchBar) {
			searchBar.setFocusable(true);
		} else {
			searchBar.setFocusable(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}

