package calvin.baidumusic.view.widget.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import calvin.baidumusic.common.Constants;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.view.MouseEventListener;
import calvin.baidumusic.view.widget.tree.IconTreeNode;

public class CustomSheetPane extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int width = 200;
	private final int height = 100;
	
	private JTextField edit = null;
	private JButton confirm = null;
	
	private int mode = 0;
	
	private CustomSheetDialog parent = null;
	
	public CustomSheetPane(CustomSheetDialog parent, int mode) {
		this.parent = parent;
		this.mode = mode;
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(248, 248, 248));
		setLayout(null);
		edit = new JTextField("");
		edit.setBounds(40, 20, 120, 30);
		edit.setForeground(new Color(63, 165, 255));
		edit.setHorizontalAlignment(JLabel.CENTER);
		add(edit);
		
		confirm = new JButton("确定");
		confirm.setBounds(75, 60, 50, 25);
		confirm.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		confirm.setBackground(new Color(63, 165, 255));
		confirm.setForeground(Color.WHITE);
		confirm.setBorder(BorderFactory.createEmptyBorder());
		confirm.setFocusable(false);
		add(confirm);
		
		MouseEventListener listener = new MouseEventListener(parent);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		confirm.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String songListName = edit.getText();
		if (songListName.equals("")) {
			parent.dispose();
			return;
		}
		if (mode == CustomSheetDialog.ADD) {
			DefaultTreeModel model = (DefaultTreeModel) PaneManager.getListPaneInstance().getTree().getModel();
			model.insertNodeInto(new IconTreeNode(new ImageIcon(Constants.LOCAL_FILE_ICON), songListName), PaneManager.getListPaneInstance().getLocalSheet(), 0);
			// 新建后展开节点
			PaneManager.getListPaneInstance().getTree().expandAll(
					PaneManager.getListPaneInstance().getTree(), new TreePath(PaneManager.getListPaneInstance().root), true);
			DaoFactory.getSongListDao().addSongList(songListName);
		} else if (mode == CustomSheetDialog.RENAME) {
			DaoFactory.getSongListDao().renameSongList(PaneManager.getListPaneInstance().getTree().node.getTxt(), songListName);
			PaneManager.getListPaneInstance().getTree().node.setTxt(songListName);
		}
		parent.dispose();
	}
}
