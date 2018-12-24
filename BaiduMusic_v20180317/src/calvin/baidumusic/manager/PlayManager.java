package calvin.baidumusic.manager;

import java.util.List;
import java.util.Random;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.util.TimeUtil;
import calvin.baidumusic.view.pane.MusicOperatePane;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import calvin.baidumusic.view.widget.table.MyTable;
import javazoom.jl.player.MyInfoListener;
import javazoom.jl.player.MyPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
/**
 * ���Ź�����
 * ʵ�����ֵĲ��ţ����ֿ������ĸ���
 * @author Calvin
 *
 */
public class PlayManager {
	
	public static MyPlayer mp = null;
	public static MyInfoListener2 listener = new MyInfoListener2();
	
	public static boolean isUserClick = false;
	public static boolean isOnline = false;
	
	public static List<Song> list2 = null;
	public static MyTable songTable2 = null;
	public static int playIndex = 0;
	
	public static int CURRENT_MODE = 0;
	
	public static void play(List<Song> list, MyTable songTable, int currentPage) {
		list2 = list;
		songTable2 = songTable;
		playIndex = songTable.getSelectedRow() + 10 * (currentPage - 1);
		String path = list.get(playIndex).getsPath();
		if (mp != null && mp.mt.isAlive()) {
//			mp.stop();
			mp.suspend();
		}
		mp = new MyPlayer(path, listener);
		// �����µ�myplayer
		mp.play();
		String totalTime = list.get(playIndex).getsDuration();
		String name = list.get(playIndex).getsName();
		String singer = list.get(playIndex).getSinger();
		refreshMusicOperatePane(totalTime, name + "-" + singer);
		isUserClick = false;
	}
	
	public static void refreshMusicOperatePane(String totalTime, String title) {
		PaneManager.getMusicOperatePaneInstance().isPlaying = true;
		PaneManager.getMusicOperatePaneInstance().songNameLabel.setText(title);
		PaneManager.getMusicOperatePaneInstance().totalTimeLabel.setText(" / " + totalTime);
		if (mp != null) {
			PaneManager.getMusicOperatePaneInstance().seekBar.setMaximum((int) mp.getTotalMs()); 
		}
		PaneManager.getMusicOperatePaneInstance().playBtn.setPauseIcon();
	}
}

//�¼�������
class MyInfoListener2 extends MyInfoListener {
	public static int frame; // �˴��������ñ�����Ŀ���ǽ���ֹͣʱ��֡�����Ա�����Ӵ˴����š�
	
	@Override
	public void playbackFinished(PlaybackEvent evt) {
//		super.playbackFinished(evt);
		System.out.println("�������");
		
		// �Զ���һ��
		if (!PlayManager.isUserClick) {
			// ����ѭ��
			if (PlayManager.CURRENT_MODE == MusicOperatePane.SINGLE_PLAY) {
				// playindex����
				PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), this);
			} else {
				if (PlayManager.playIndex <= PlayManager.list2.size() - 2) {
					// ˳�򲥷�
					if (PlayManager.CURRENT_MODE == MusicOperatePane.ORDER_PLAY) {
						// playindex++
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(++PlayManager.playIndex).getsPath(), this);
						// �������
					} else if (PlayManager.CURRENT_MODE == MusicOperatePane.RANDOM_PLAY) {
						// playindex���
						PlayManager.playIndex = new Random().nextInt(PlayManager.list2.size());
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), this);
					}
				} else {
					PaneManager.getMusicOperatePaneInstance().getPlayBtn().setPlayIcon();
					new MessageDialog("�Ѿ������һ�׸���");
					PlayManager.playIndex = PlayManager.list2.size() - 1;
				}
			}
			if (PlayManager.mp != null) {
				PlayManager.mp.play();
			}
			String totalTime = PlayManager.list2.get(PlayManager.playIndex).getsDuration();
			String name = PlayManager.list2.get(PlayManager.playIndex).getsName();
			String singer = PlayManager.list2.get(PlayManager.playIndex).getSinger();
			PlayManager.refreshMusicOperatePane(totalTime, name + "-" + singer);
			PaneManager.getMusicOperatePaneInstance().loadLrc();
		}
	}
	
	// ������Լÿ100ms������һ���¼�
	@Override
	public void playbackTick(PlaybackEvent evt) {
		super.playbackTick(evt);
		PaneManager.getMusicOperatePaneInstance().seekBar.setValue(PlayManager.mp.getCurrentTime());
		PaneManager.getMusicOperatePaneInstance().currentTimeLabel.setText(TimeUtil.formatTime(PlayManager.mp.getCurrentTime()));
	}
}