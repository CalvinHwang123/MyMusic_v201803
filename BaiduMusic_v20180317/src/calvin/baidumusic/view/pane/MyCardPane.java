package calvin.baidumusic.view.pane;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import calvin.baidumusic.manager.PaneManager;
/**
 * ��Ƭ���ֵĸ����
 * ����ʢ�ű���������塢����������塢�Ƹ赥�����Զ���赥����
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
		
		// �ڶ�������Ϊcardlayout��jpanel�ڲ�������
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
