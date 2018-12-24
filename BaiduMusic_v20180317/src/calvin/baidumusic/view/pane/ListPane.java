package calvin.baidumusic.view.pane;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import calvin.baidumusic.bean.SongList;
import calvin.baidumusic.common.Constants;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.view.widget.tree.IconTreeNode;
import calvin.baidumusic.view.widget.tree.MyTree;
/**
 * 头列表部面板
 * 提供我的音乐（本地音乐、下载列表）、在线音乐、
 * 云歌单（收藏列表）和自定义歌单等操作
 * @author Calvin
 *
 */
public class ListPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 175;
	private final int HEIGHT = 492;

	private MyTree tree = null;
	public IconTreeNode root = null;
	private IconTreeNode localSheet = null;
	
	public ListPane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
//		setLayout(null);
		setBackground(Color.WHITE);
		
		// 去除默认边框
		setBorder(BorderFactory.createEmptyBorder());
		// 隐藏滚动条
		getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		initTree();
		
	}

	public MyTree getTree() {
		return tree;
	}

	public void setTree(MyTree tree) {
		this.tree = tree;
	}

	private void initTree() {
		root = new IconTreeNode("root");
		// 我的音乐节点部分
		// 包括：本地音乐和下载列表
		IconTreeNode myMusic = new IconTreeNode("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='微软雅黑'>我的音乐</font></html>");
		IconTreeNode localMusic = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "本地音乐");
		IconTreeNode downloadList = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "下载列表");
		myMusic.add(localMusic);
		myMusic.add(downloadList);
		root.add(myMusic);
		// 在线歌曲节点部分
		// 包括：在线音乐和收藏列表
		IconTreeNode onlineSong = new IconTreeNode("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='微软雅黑'>在线歌曲</font></html>");
		IconTreeNode onlineMusic = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "在线音乐");
		IconTreeNode favorite = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "收藏列表");
		onlineSong.add(onlineMusic);
		onlineSong.add(favorite);
		root.add(onlineSong);
		// 自定义歌单节点部分
		localSheet = new IconTreeNode("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='微软雅黑'>本地歌单</font></html>");
		// 查找保存的本地歌单节点
		List<SongList> songList = DaoFactory.getSongListDao().listSongListName();
		for (SongList sl: songList) {
			IconTreeNode sheet = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), sl.getSlName());
			localSheet.add(sheet);
		}
		root.add(localSheet);
		
		tree = new MyTree(root);
		tree.setBounds(30, 0, WIDTH - 30, HEIGHT);
		setViewportView(tree);
	}

	public IconTreeNode getLocalSheet() {
		return localSheet;
	}

	public void setLocalSheet(IconTreeNode localSheet) {
		this.localSheet = localSheet;
	}

}
