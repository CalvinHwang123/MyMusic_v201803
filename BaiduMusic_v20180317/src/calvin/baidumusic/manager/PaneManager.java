package calvin.baidumusic.manager;

import javax.swing.JFrame;

import calvin.baidumusic.view.pane.DownloadListPane;
import calvin.baidumusic.view.pane.FavoritePane;
import calvin.baidumusic.view.pane.HeadPane;
import calvin.baidumusic.view.pane.ListPane;
import calvin.baidumusic.view.pane.LocalMusicPane;
import calvin.baidumusic.view.pane.LocalSongSheetPane;
import calvin.baidumusic.view.pane.LrcPane;
import calvin.baidumusic.view.pane.MusicOperatePane;
import calvin.baidumusic.view.pane.MyCardPane;
import calvin.baidumusic.view.pane.OnlineMusicPane;
import calvin.baidumusic.view.pane.SearchResultPane;

/**
 * 面板管理类
 * 实现单例面板类
 * @author Calvin
 *
 */
public class PaneManager {
	public static FavoritePane favoritePane = null;
	public static HeadPane headPane = null;
	public static ListPane listPane = null;
	public static LocalMusicPane localMusicPane = null;
	public static DownloadListPane downloadListPane = null;
	public static LocalSongSheetPane localSongSheetPane = null;
	public static LrcPane lrcPane = null;
	public static MusicOperatePane musicOperatePane = null;
	public static OnlineMusicPane onlineMusicPane = null;
	private static SearchResultPane searchResultPane = null;
	
	public static MyCardPane myCardPane = null;
	
	private PaneManager() {}
	
	public static FavoritePane getFavoritePaneInstance() {
		if (favoritePane == null) {
			favoritePane = new FavoritePane();
		}
		return favoritePane;
	}
	
	public static HeadPane getHeadPaneInstance(JFrame frame) {
		if (headPane == null) {
			headPane = new HeadPane(frame);
		}
		return headPane;
	}
	
	public static ListPane getListPaneInstance() {
		if (listPane == null) {
			listPane = new ListPane();
		}
		return listPane;
	}
	
	public static LocalMusicPane getLocalMusicPaneInstance() {
		if (localMusicPane == null) {
			localMusicPane = new LocalMusicPane();
		}
		return localMusicPane;
	}
	
	public static DownloadListPane getDownloadListPaneInstance() {
		if (downloadListPane == null) {
			downloadListPane = new DownloadListPane();
		}
		return downloadListPane;
	}
	
	public static LocalSongSheetPane getLocalSongSheetPaneInstance() {
		if (localSongSheetPane == null) {
			localSongSheetPane = new LocalSongSheetPane();
		}
		return localSongSheetPane;
	}
	
	public static LrcPane getLrcPaneInstance() {
		if (lrcPane == null) {
			lrcPane = new LrcPane();
		}
		return lrcPane;
	}
	
	public static MusicOperatePane getMusicOperatePaneInstance() {
		if (musicOperatePane == null) {
			musicOperatePane = new MusicOperatePane();
		}
		return musicOperatePane;
	}
	
	public static OnlineMusicPane getOnlineMusicPaneInstance() {
		if (onlineMusicPane == null) {
			onlineMusicPane = new OnlineMusicPane();
		}
		return onlineMusicPane;
	}
	
	public static SearchResultPane getSearchResultPaneInstance() {
		if (searchResultPane == null) {
			searchResultPane = new SearchResultPane();
		}
		return searchResultPane;
	}
	
	
	public static MyCardPane getMyCardPaneInstance() {
		if (myCardPane == null) {
			myCardPane = new MyCardPane();
		}
		return myCardPane;
	}
}
