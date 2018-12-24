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
 * 播放管理类
 * 实现音乐的播放，音乐控制面板的更新
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
		// 播放新的myplayer
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

//事件监听器
class MyInfoListener2 extends MyInfoListener {
	public static int frame; // 此处设置引用变量的目的是接收停止时的帧数，以便继续从此处播放。
	
	@Override
	public void playbackFinished(PlaybackEvent evt) {
//		super.playbackFinished(evt);
		System.out.println("播放完成");
		
		// 自动下一首
		if (!PlayManager.isUserClick) {
			// 单曲循环
			if (PlayManager.CURRENT_MODE == MusicOperatePane.SINGLE_PLAY) {
				// playindex不变
				PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), this);
			} else {
				if (PlayManager.playIndex <= PlayManager.list2.size() - 2) {
					// 顺序播放
					if (PlayManager.CURRENT_MODE == MusicOperatePane.ORDER_PLAY) {
						// playindex++
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(++PlayManager.playIndex).getsPath(), this);
						// 随机播放
					} else if (PlayManager.CURRENT_MODE == MusicOperatePane.RANDOM_PLAY) {
						// playindex随机
						PlayManager.playIndex = new Random().nextInt(PlayManager.list2.size());
						PlayManager.mp = new MyPlayer(PlayManager.list2.get(PlayManager.playIndex).getsPath(), this);
					}
				} else {
					PaneManager.getMusicOperatePaneInstance().getPlayBtn().setPlayIcon();
					new MessageDialog("已经是最后一首歌了");
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
	
	// 播放中约每100ms触发的一次事件
	@Override
	public void playbackTick(PlaybackEvent evt) {
		super.playbackTick(evt);
		PaneManager.getMusicOperatePaneInstance().seekBar.setValue(PlayManager.mp.getCurrentTime());
		PaneManager.getMusicOperatePaneInstance().currentTimeLabel.setText(TimeUtil.formatTime(PlayManager.mp.getCurrentTime()));
	}
}