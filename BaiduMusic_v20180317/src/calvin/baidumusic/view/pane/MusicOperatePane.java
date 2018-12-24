package calvin.baidumusic.view.pane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;

import calvin.baidumusic.common.Constants;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.manager.PlayManager;
import calvin.baidumusic.util.TimeUtil;
import calvin.baidumusic.view.widget.button.MyButton;
import calvin.baidumusic.view.widget.button.PlayButton;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import calvin.baidumusic.view.widget.scrolllist.TestLRC;
import calvin.baidumusic.view.widget.slider.MySlider;
import javazoom.jl.player.MyPlayer;

/**
 * ���ֲ������ 
 * ��Ҫʵ����һ�ס�����/��ͣ����һ�� 
 * ��������ʱ�������⡢�����������ʾ������ģʽ�������б��
 * @author Calvin
 *
 */
public class MusicOperatePane extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1000;
	private final int HEIGHT = 72;

	private MyButton previousBtn = null;
	public PlayButton playBtn = null;
	private MyButton nextBtn = null;
	public boolean isPlaying = false;// ���ڲ��ű��

	public MySlider seekBar = null;
	public JLabel currentTimeLabel = null;
	public JLabel totalTimeLabel = null;
	public JLabel songNameLabel = null;
	public static int targetValue = 0;
	public static boolean isDragged = false; 

	private MyButton lyricBtn = null;
	private MyButton playModeBtn = null;
	
	public static final int ORDER_PLAY = 0;// Ĭ��Ϊ˳�򲥷�  
	public static final int RANDOM_PLAY = 1;// �������
	public static final int SINGLE_PLAY = 2;// ����ѭ�� 

	public MusicOperatePane() {
		initUI();
	}

	private void initUI() {
		setBackground(new Color(60, 60, 60));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);

		previousBtn = new MyButton("��һ��", Constants.PREV_DEF, Constants.PREV_ROLLOVER, Constants.PREV_PRESSED, 50, 50);
		previousBtn.setLocation(8, 11);
		add(previousBtn);
		playBtn = new PlayButton("����", "��ͣ", Constants.PLAY_DEF, Constants.PLAY_ROLLOVER, Constants.PLAY_PRESSED, 50, 50);
		playBtn.setLocation(70, 11);
		add(playBtn);
		nextBtn = new MyButton("��һ��", Constants.NEXT_DEF, Constants.NEXT_ROLLOVER, Constants.NEXT_PRESSED, 50, 50);
		nextBtn.setLocation(132, 11);
		add(nextBtn);

		seekBar = new MySlider();
		seekBar.setBounds(270, 38, 485, 20);
		seekBar.setValue(0);
//		seekBar.setMaximum((int) MainFrame.mp.getTotalMs());
		add(seekBar);

//		currentTimeLabel = new JLabel(TimeUtil.formatTime(MainFrame.mp.getCurrentTime()));
		currentTimeLabel = new JLabel("00:00");
		currentTimeLabel.setBounds(680, 13, 35, 25);
		currentTimeLabel.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		currentTimeLabel.setForeground(Color.WHITE);
		add(currentTimeLabel);

//		totalTimeLabel = new JLabel(" / " + TimeUtil.formatTime((long) MainFrame.mp.getTotalMs()));
		totalTimeLabel = new JLabel(" / 00:00");
		totalTimeLabel.setBounds(710, 13, 45, 25);
		totalTimeLabel.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		totalTimeLabel.setForeground(Color.WHITE);
		add(totalTimeLabel);

		songNameLabel = new JLabel("�ٶ����� ��������");
		songNameLabel.setBounds(400, 10, 200, 20);
		songNameLabel.setHorizontalAlignment(JLabel.CENTER);
		songNameLabel.setForeground(Color.WHITE);
		add(songNameLabel);
		
		lyricBtn = new MyButton("�����ʾ", Constants.BTN_LYRIC, Constants.BTN_LYRIC2, Constants.BTN_LYRIC3, 44, 44);
		lyricBtn.setLocation(890, 13);
		add(lyricBtn);
		playModeBtn = new MyButton("����ģʽ", Constants.BTN_PLAY_MODE_NEXT, Constants.BTN_PLAY_MODE_NEXT2, Constants.BTN_PLAY_MODE_NEXT3, 44, 44);
		playModeBtn.setLocation(930, 13);
		add(playModeBtn);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		previousBtn.addActionListener(this);
		playBtn.addActionListener(this);
		nextBtn.addActionListener(this);
		lyricBtn.addMouseListener(this);
		playModeBtn.addMouseListener(this);
//		seekBar.addMouseMotionListener(this);
//		seekBar.addMouseListener(this);
	}

	public PlayButton getPlayBtn() {
		return playBtn;
	}

	public void setPlayBtn(PlayButton playBtn) {
		this.playBtn = playBtn;
	}

	public JSlider getSeekBar() {
		return seekBar;
	}

	public void setSeekBar(MySlider seekBar) {
		this.seekBar = seekBar;
	}

	public JLabel getTotalTimeLabel() {
		return totalTimeLabel;
	}

	public void setTotalTimeLabel(JLabel totalTimeLabel) {
		this.totalTimeLabel = totalTimeLabel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == playBtn) {
			if (!isPlaying) {
				if (PlayManager.mp != null) {
					PlayManager.mp.resume();
				}
				if (PlayManager.list2 == null || PlayManager.list2.size() == 0) {
					new MessageDialog("��δѡ�и���");
					return;
				}
				playBtn.setPauseIcon();
				isPlaying = true;
			} else {
				if (PlayManager.mp != null) {
					PlayManager.mp.suspend();
				}
				playBtn.setPlayIcon();
				isPlaying = false;
				PlayManager.isUserClick = false;
			}
		} else if (obj == previousBtn) {
			if (PlayManager.mp != null
					&& isPlaying) {
				PlayManager.isUserClick = true;
				PlayManager.mp.stop();
				PlayManager.mp = null;
			}
			
			if (PlayManager.list2 != null && PlayManager.list2.size() > 0) {
				// ����ѭ��
				if (PlayManager.CURRENT_MODE == SINGLE_PLAY) {
					// playindexһֱ����
					PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), PlayManager.listener);
				}
				// ��һ��
				if (PlayManager.playIndex >= 1) {
					// ˳�򲥷�
					if (PlayManager.CURRENT_MODE == ORDER_PLAY) {
						// playindex--
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(--PlayManager.playIndex).getsPath(), PlayManager.listener);
						// �������
					} else if (PlayManager.CURRENT_MODE == RANDOM_PLAY) {
						// playindex���
						PlayManager.playIndex = new Random().nextInt(PlayManager.list2.size());
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), PlayManager.listener);
					}
				} else {
					PaneManager.getMusicOperatePaneInstance().getPlayBtn().setPlayIcon();
					new MessageDialog("�Ѿ��ǵ�һ�׸���");
					PlayManager.playIndex = 0;
					return;
				}
				
				if (PlayManager.mp != null) {
					PlayManager.mp.play();
					PlayManager.isUserClick = false;
				}
				String totalTime = PlayManager.list2.get(PlayManager.playIndex).getsDuration();
				String name = PlayManager.list2.get(PlayManager.playIndex).getsName();
				String singer = PlayManager.list2.get(PlayManager.playIndex).getSinger();
				PlayManager.refreshMusicOperatePane(totalTime, name + "-" + singer);
				
				loadLrc();
			} else {
				PaneManager.getMusicOperatePaneInstance().getPlayBtn().setPlayIcon();
				new MessageDialog("��δѡ�и���");
				return;
			}
			
		} else if (obj == nextBtn) {
			if (PlayManager.mp != null
					&& isPlaying) {
				PlayManager.isUserClick = true;
				PlayManager.mp.stop();
				PlayManager.mp = null;
			}
			
			if (PlayManager.list2 != null && PlayManager.list2.size() > 0) {
				// ����ѭ��
				if (PlayManager.CURRENT_MODE == 2) {
					// playindexһֱ����
					PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), PlayManager.listener);
				}
				// ��һ��
				if (PlayManager.playIndex <= PlayManager.list2.size() - 2) {
					// ˳�򲥷�
					if (PlayManager.CURRENT_MODE == 0) {
						// playindex++
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(++PlayManager.playIndex).getsPath(), PlayManager.listener);
						// �������
					} else if (PlayManager.CURRENT_MODE == 1) {
						// playindex���
						PlayManager.playIndex = new Random().nextInt(PlayManager.list2.size());
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), PlayManager.listener);
					}
				} else {
					PaneManager.getMusicOperatePaneInstance().getPlayBtn().setPlayIcon();
					new MessageDialog("�Ѿ������һ�׸���");
					PlayManager.playIndex = PlayManager.list2.size() - 1;
					return;
				}
				
				if (PlayManager.mp != null) {
					PlayManager.mp.play();
					PlayManager.isUserClick = false;
				}
				String totalTime = PlayManager.list2.get(PlayManager.playIndex).getsDuration();
				String name = PlayManager.list2.get(PlayManager.playIndex).getsName();
				String singer = PlayManager.list2.get(PlayManager.playIndex).getSinger();
				PlayManager.refreshMusicOperatePane(totalTime, name + "-" + singer);
				
				loadLrc();
			} else {
				PaneManager.getMusicOperatePaneInstance().getPlayBtn().setPlayIcon();
				new MessageDialog("��δѡ�и���");
				return;
			}
		}
	}

	 public void loadLrc() {
		PaneManager.getLrcPaneInstance().lrcList.stop();
		List<Map<Long, String>> list = null;
		try {
			list = new TestLRC().parse(PlayManager.list2.get(PlayManager.playIndex).getsName() + "-"
					+ PlayManager.list2.get(PlayManager.playIndex).getSinger() + ".lrc");
		} catch (Exception e) {
			System.out.println("�Ҳ���ָ�����ļ�");
		}
		if (list != null && list.size() > 0) {
			PaneManager.getLrcPaneInstance().lrcList.initLrcList(list);
			PaneManager.getLrcPaneInstance().lrcList.startScroll();
		} else {
			PaneManager.getLrcPaneInstance().lrcList.lrcUnavalible();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == seekBar) {
			PlayManager.mp.suspend();
			targetValue = seekBar.getValue();
			seekBar.setValue(targetValue);
			currentTimeLabel.setText(TimeUtil.formatTime(targetValue));
//			isDragged = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == playModeBtn) {
			JPopupMenu pop = new JPopupMenu();
			JMenuItem orderPlay = new JMenuItem("˳�򲥷�");
			JMenuItem randomPlay = new JMenuItem("�������");
			JMenuItem singlePlay = new JMenuItem("����ѭ��");
			pop.add(orderPlay);
			pop.add(randomPlay);
			pop.add(singlePlay);
			pop.show(e.getComponent(), e.getX(), e.getY());
			orderPlay.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PlayManager.CURRENT_MODE = 0;
					playModeBtn.setIcon(new ImageIcon(Constants.BTN_PLAY_MODE_NEXT));
					playModeBtn.setRolloverIcon(new ImageIcon(Constants.BTN_PLAY_MODE_NEXT2));
					playModeBtn.setPressedIcon(new ImageIcon(Constants.BTN_PLAY_MODE_NEXT3));
					new MessageDialog("˳�򲥷�������");
				}
			});
			randomPlay.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PlayManager.CURRENT_MODE = 1;
					playModeBtn.setIcon(new ImageIcon(Constants.BTN_PLAY_MODE_RAND));
					playModeBtn.setRolloverIcon(new ImageIcon(Constants.BTN_PLAY_MODE_RAND2));
					playModeBtn.setPressedIcon(new ImageIcon(Constants.BTN_PLAY_MODE_RAND3));
					new MessageDialog("�������������");
				}
			});
			singlePlay.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PlayManager.CURRENT_MODE = 2;
					playModeBtn.setIcon(new ImageIcon(Constants.BTN_PLAY_MODE_SINGLE_CIRCLE));
					playModeBtn.setRolloverIcon(new ImageIcon(Constants.BTN_PLAY_MODE_SINGLE_CIRCLE2));
					playModeBtn.setPressedIcon(new ImageIcon(Constants.BTN_PLAY_MODE_SINGLE_CIRCLE3));
					new MessageDialog("����ѭ��������");
				}
			});
		} else if (obj == lyricBtn) {
			JPopupMenu pop = new JPopupMenu();
			JMenuItem open = new JMenuItem("�򿪸��");
			JMenuItem close = new JMenuItem("�رո��");
			pop.add(open);
			pop.add(close);
			pop.show(e.getComponent(), e.getX(), e.getY());
			open.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (PlayManager.list2 != null && PlayManager.list2.size() > 0) {
						loadLrc();
						PaneManager.getMyCardPaneInstance().cardLayout.show(PaneManager.getMyCardPaneInstance(), MyCardPane.LYRICPANE);
					} else {
						new MessageDialog("����˫�����Ÿ���");
					}
				}
			});
			close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (PlayManager.list2 != null && PlayManager.list2.size() > 0) {
						PaneManager.getLrcPaneInstance().lrcList.timer.stop();
						PaneManager.getMyCardPaneInstance().cardLayout.show(PaneManager.getMyCardPaneInstance(), MyCardPane.LOCALMUSICPANE);
					} else {
						new MessageDialog("����˫�����Ÿ���");
					}
				}
			});
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == seekBar) {
			System.out.println("realsed value = " + targetValue);
			PlayManager.mp.resume();
			PlayManager.mp.play(targetValue);
//			isDragged = true;
//			seekBar.setValue(targetValue);
//			currentTimeLabel.setText(TimeUtil.formatTime(targetValue));
//			isDragged = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
