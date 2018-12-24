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
 * ͷ�б����
 * �ṩ�ҵ����֣��������֡������б����������֡�
 * �Ƹ赥���ղ��б����Զ���赥�Ȳ���
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
		
		// ȥ��Ĭ�ϱ߿�
		setBorder(BorderFactory.createEmptyBorder());
		// ���ع�����
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
		// �ҵ����ֽڵ㲿��
		// �������������ֺ������б�
		IconTreeNode myMusic = new IconTreeNode("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='΢���ź�'>�ҵ�����</font></html>");
		IconTreeNode localMusic = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "��������");
		IconTreeNode downloadList = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "�����б�");
		myMusic.add(localMusic);
		myMusic.add(downloadList);
		root.add(myMusic);
		// ���߸����ڵ㲿��
		// �������������ֺ��ղ��б�
		IconTreeNode onlineSong = new IconTreeNode("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='΢���ź�'>���߸���</font></html>");
		IconTreeNode onlineMusic = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "��������");
		IconTreeNode favorite = new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), "�ղ��б�");
		onlineSong.add(onlineMusic);
		onlineSong.add(favorite);
		root.add(onlineSong);
		// �Զ���赥�ڵ㲿��
		localSheet = new IconTreeNode("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='΢���ź�'>���ظ赥</font></html>");
		// ���ұ���ı��ظ赥�ڵ�
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
