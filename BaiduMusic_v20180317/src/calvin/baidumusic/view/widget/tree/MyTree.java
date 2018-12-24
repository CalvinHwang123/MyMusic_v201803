package calvin.baidumusic.view.widget.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.bean.SongList;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.test.MyTest;
import calvin.baidumusic.view.pane.MyCardPane;
import calvin.baidumusic.view.widget.dialog.CustomSheetDialog;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import net.sf.json.JSONObject;

public class MyTree extends JTree implements MouseListener, TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	
	public IconTreeNode node = null;

	public MyTree(TreeNode root) {
		super(root);
		setRootVisible(false);
		setRowHeight(40);
		putClientProperty("JTree.lineStyle", "None");
		setShowsRootHandles(false);
//		setToggleClickCount(1);
		setShowsRootHandles(false);// �������Сͼ��
		setCellRenderer(new MyTreeCellRenderer());
		
		expandAll(this, new TreePath(root), true);
		
		addMouseListener(this);
		addTreeSelectionListener(this);
	}

	public void expandAll(JTree tree, TreePath parent, boolean expand) {
		 // Traverse children 
        TreeNode node = (TreeNode) parent.getLastPathComponent(); 
        if (node.getChildCount() >= 0) { 
            for (@SuppressWarnings("rawtypes")
			Enumeration e = node.children(); e.hasMoreElements(); ) { 
                TreeNode n = (TreeNode) e.nextElement(); 
                TreePath path = parent.pathByAddingChild(n); 
                expandAll(tree, path, expand); 
            } 
        } 
        // Expansion or collapse must be done bottom-up 
        if (expand) { 
            tree.expandPath(parent); 
        } else { 
            tree.collapsePath(parent); 
        } 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		node = (IconTreeNode) getLastSelectedPathComponent();
		if (e.getButton()==MouseEvent.BUTTON3) {
			if (node == null) {
				return;
			}
			if (node.getTxt().equals("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='΢���ź�'>���ظ赥</font></html>")) {
				JPopupMenu pop = new JPopupMenu();
				JMenuItem create = new JMenuItem("�½��赥");
				pop.add(create);
				pop.show(e.getComponent(), e.getX(), e.getY());
				create.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new CustomSheetDialog(CustomSheetDialog.ADD);
					}
				});
			} else if (node.isLeaf() && 
					((IconTreeNode) node.getParent()).getTxt()
						.equals("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='΢���ź�'>���ظ赥</font></html>")) {
				JPopupMenu pop = new JPopupMenu();
				JMenuItem delete = new JMenuItem("ɾ��");
				JMenuItem rename = new JMenuItem("������");
				pop.add(delete);
				pop.add(rename);
				pop.show(e.getComponent(), e.getX(), e.getY());
				delete.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						DefaultTreeModel model = (DefaultTreeModel) PaneManager.getListPaneInstance().getTree().getModel();
						model.removeNodeFromParent((MutableTreeNode) node);
						DaoFactory.getSongListDao().deleteSongList(node.getTxt());
					}
				});
				rename.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new CustomSheetDialog(CustomSheetDialog.RENAME);
					}
				});
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		IconTreeNode node = (IconTreeNode) getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.isLeaf()) {
			switch(node.getTxt()) {
			case "��������":
				System.out.println("�򿪱����������");
				PaneManager.getMyCardPaneInstance().getCardLayout()
					.show(PaneManager.getMyCardPaneInstance(), MyCardPane.LOCALMUSICPANE);
				// ���¼���һ�鱾���������ݣ�������һ�����Ƴ����Ĳ�����
				PaneManager.getLocalMusicPaneInstance().initLocalDatabaseMusic();
				break;
			case "�����б�":
				System.out.println("�������б����");
				PaneManager.getMyCardPaneInstance().getCardLayout()
				.show(PaneManager.getMyCardPaneInstance(), MyCardPane.DOWNLOADLISTPANE);
			// ���¼���һ�������б����ݣ�������һ�����Ƴ����Ĳ�����
			PaneManager.getDownloadListPaneInstance().initDownloadListMusic();
				break;
			case "��������":
				if (MyTest.ct == null) {
					new MessageDialog("������δ����");
					return;
				}
				try {
					MyTest.ct.getClientStream().send("{type: 'music'}");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("�������������");
				PaneManager.getMyCardPaneInstance().getCardLayout()
				.show(PaneManager.getMyCardPaneInstance(), MyCardPane.ONLINEMUSICPANE);
				break;
			case "�ղ��б�":
				if (PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).user == null) {
					new MessageDialog("���ȵ�¼");
					return;
				}
				try {
					JSONObject jo = new JSONObject();
					jo.put("type", "get_collect");
					jo.put("user_id", PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).user.getuId());
					MyTest.ct.getClientStream().send(jo.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("���ղ��б����");
				PaneManager.getMyCardPaneInstance().getCardLayout()
				.show(PaneManager.getMyCardPaneInstance(), MyCardPane.MYFAVORITEPANE);
				break;
			}
		}
		if (node.isLeaf() && 
				((IconTreeNode) node.getParent()).getTxt()
					.equals("<html>&nbsp;&nbsp;&nbsp;&nbsp;<font face='΢���ź�'>���ظ赥</font></html>")) {
			List<SongList> list = DaoFactory.getSongListDao().listSongListName();
			for (SongList sl: list) {
				if (node.getTxt().equals(sl.getSlName())) {
					List<Song> listAllSong = DaoFactory.getSSLOperateDao().listAllSong(sl.getSlName());
//					System.out.println(node.getTxt() + "������ " + listAllSong.size());
					System.out.println("�򿪱��ظ赥���");
					PaneManager.getMyCardPaneInstance().getCardLayout()
					.show(PaneManager.getMyCardPaneInstance(), MyCardPane.SONGSHEETPANE);
					updateSheetTable(listAllSong, node);
				}
			}
		}
	}
	private void updateSheetTable(List<Song> listAllSong, IconTreeNode node) {
		PaneManager.getLocalSongSheetPaneInstance().songSum.setText(node.getTxt() + " " +listAllSong.size() + "��");
		PaneManager.getLocalSongSheetPaneInstance().localSheetSum = listAllSong.size();
		PaneManager.getLocalSongSheetPaneInstance().songTable.updateCurrentPage(listAllSong, 1);
		PaneManager.getLocalSongSheetPaneInstance().list = listAllSong;
		PaneManager.getLocalSongSheetPaneInstance().subList = PaneManager.getLocalSongSheetPaneInstance().songTable.updateCurrentPage(listAllSong, 1);
		PaneManager.getLocalSongSheetPaneInstance().currentpage = 1;
		PaneManager.getLocalSongSheetPaneInstance().pageSum = listAllSong.size() % 10 == 0 ? listAllSong.size() / 10 : listAllSong.size() / 10 + 1;
		PaneManager.getLocalSongSheetPaneInstance().pageLabel.setText("1/"+PaneManager.getLocalSongSheetPaneInstance().pageSum);
	}
}
