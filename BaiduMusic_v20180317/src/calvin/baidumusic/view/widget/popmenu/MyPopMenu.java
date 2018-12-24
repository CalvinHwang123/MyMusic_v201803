package calvin.baidumusic.view.widget.popmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.PaneManager;

public class MyPopMenu extends JPopupMenu implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JMenuItem asc = null;
	private JMenuItem asc2 = null;
	private JMenuItem asc3 = null;
	private JMenuItem asc4 = null;
	private JMenuItem desc = null;
	private JMenuItem desc2 = null;
	private JMenuItem desc3 = null;
	private JMenuItem desc4 = null;

	public void show(MouseEvent e) {
		JMenu name = new JMenu("°´¸èÇú");
		JMenu singer = new JMenu("°´¸èÊÖ");
		JMenu size = new JMenu("°´´óÐ¡");
		JMenu duration = new JMenu("°´Ê±¼ä");
		asc = new JMenuItem("ÉýÐò");
		desc = new JMenuItem("½µÐò");
		asc2 = new JMenuItem("ÉýÐò");
		desc2 = new JMenuItem("½µÐò");
		asc3 = new JMenuItem("ÉýÐò");
		desc3 = new JMenuItem("½µÐò");
		asc4 = new JMenuItem("ÉýÐò");
		desc4 = new JMenuItem("½µÐò");
		name.add(asc);
		singer.add(asc2);
		size.add(asc3);
		duration.add(asc4);
		name.add(desc);
		singer.add(desc2);
		size.add(desc3);
		duration.add(desc4);
		add(name);
		add(singer);
		add(size);
		add(duration);
		show(e.getComponent(), e.getX(), e.getY());
		asc.addActionListener(this);
		asc2.addActionListener(this);
		asc3.addActionListener(this);
		asc4.addActionListener(this);
		desc.addActionListener(this);
		desc2.addActionListener(this);
		desc3.addActionListener(this);
		desc4.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == asc) {
			updateTable(DaoFactory.getSongDao().listAllSong(0));
		} else if (obj == desc) {
			updateTable(DaoFactory.getSongDao().listAllSong(1));
		} else if (obj == asc2) {
			updateTable(DaoFactory.getSongDao().listAllSong(2));
		} else if (obj == desc2) {
			updateTable(DaoFactory.getSongDao().listAllSong(3));
		} else if (obj == asc3) {
			updateTable(DaoFactory.getSongDao().listAllSong(4));
		} else if (obj == desc3) {
			updateTable(DaoFactory.getSongDao().listAllSong(5));
		} else if (obj == asc4) {
			updateTable(DaoFactory.getSongDao().listAllSong(6));
		} else if (obj == desc4) {
			updateTable(DaoFactory.getSongDao().listAllSong(7));
		}
	}
	
	private void updateTable(List<Song> list) {
		PaneManager.getLocalMusicPaneInstance().songSum.setText("±¾µØÒôÀÖ " +list.size() + "Ê×");
		PaneManager.getLocalMusicPaneInstance().localMusicSum = list.size();
		PaneManager.getLocalMusicPaneInstance().songTable.updateCurrentPage(list, 1);
		PaneManager.getLocalMusicPaneInstance().list = list;
		PaneManager.getLocalMusicPaneInstance().subList = PaneManager.getLocalMusicPaneInstance().songTable.updateCurrentPage(list, 1);
		PaneManager.getLocalMusicPaneInstance().currentpage = 1;
		PaneManager.getLocalMusicPaneInstance().pageSum = list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		PaneManager.getLocalMusicPaneInstance().pageLabel.setText("1/"+PaneManager.getLocalMusicPaneInstance().pageSum);
	}
}
