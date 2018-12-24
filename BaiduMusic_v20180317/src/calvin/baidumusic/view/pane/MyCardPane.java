package calvin.baidumusic.view.pane;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import calvin.baidumusic.manager.PaneManager;
/**
 * 卡片布局的父面板
 * 用来盛放本地音乐面板、音乐下载面板、云歌单面板和自定义歌单面板等
 * @author Calvin
 *
 */
public class MyCardPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 825;
	private final int HEIGHT = 492;
	
	public CardLayout cardLayout = null;
	
	public static final String LOCALMUSICPANE =  "localMusicPane";
	public static final String DOWNLOADLISTPANE =  "donwnloadListPane";
	public static final String ONLINEMUSICPANE =  "onlineMusicPane";
	public static final String MYFAVORITEPANE =  "myFavoritePane";
	public static final String SONGSHEETPANE =  "songSheetPane";
	public static final String LYRICPANE =  "lyricPane";
	public static final String SEARCHRESULTPANE =  "searchResultPane";
	
	public MyCardPane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		
		// 第二个参数为cardlayout中jpanel内部表名称
		add(PaneManager.getLocalMusicPaneInstance(), MyCardPane.LOCALMUSICPANE);
		add(PaneManager.getDownloadListPaneInstance(), MyCardPane.DOWNLOADLISTPANE);
		add(PaneManager.getOnlineMusicPaneInstance(), MyCardPane.ONLINEMUSICPANE);
		add(PaneManager.getFavoritePaneInstance(), MyCardPane.MYFAVORITEPANE);
		add(PaneManager.getLocalSongSheetPaneInstance(), MyCardPane.SONGSHEETPANE);
		add(PaneManager.getLrcPaneInstance(), MyCardPane.LYRICPANE);
		add(PaneManager.getSearchResultPaneInstance(), MyCardPane.SEARCHRESULTPANE);
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}
	
}
