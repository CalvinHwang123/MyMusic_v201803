package calvin.baidumusic.view.pane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.bean.SongList;
import calvin.baidumusic.client.FileDownloadThread;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.PlayManager;
import calvin.baidumusic.util.SongInfoProcessor;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import calvin.baidumusic.view.widget.table.MyTable;
/**
 * 下载列表面板
 * 主要显示本地下载音乐列表音乐列表
 * @author Calvin
 *
 */
public class DownloadListPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int WIDTH = 825;
	private final int HEIGHT = 492;
	
	JLabel songSum = null;
	public int downloadListSum = 0;
	private static final Object[] colData = {0, "歌曲", "歌手", "大小", "时间", "路径"};
	public MyTable songTable = null;
	
	private JButton prev = null;
	private JButton next = null;
	private JButton jump = null;
	private JTextField jumpEdit = null;
	
	private List<Song> list = new ArrayList<>();
	private List<Song> subList = new ArrayList<>();
	
	int currentpage = 1;
	int pageSum = 0;
	public JLabel pageLabel = null;
	
	public DownloadListPane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		
		initHeadInfo();
		initTableInfo();
		initDownloadListMusic();
		
	}

	public void initDownloadListMusic() {
		List<String> filePathList = new ArrayList<>();
		File dir = new File(FileDownloadThread.filePrex);
		if (dir.exists()) {
			File[] f = dir.listFiles();
			for (File temp: f) {
				filePathList.add(temp.getAbsolutePath());
			}
			list = formatSongInfo(filePathList);
		}
		
		subList = songTable.updateCurrentPage(list, 1);
		pageSum = list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		if (list != null && list.size() > 0) {
			songTable.updateCurrentPage(list, 1);
			songSum.setText("下载列表 " + list.size() + "首");
			downloadListSum = list.size();
			pageLabel.setText("1/" + pageSum);
		}
	}

	private void initTableInfo() {
		// 刚开始DefaultTableModel没有数据
		songTable = new MyTable(new DefaultTableModel(colData, 0), WIDTH, 415, this);
		add(songTable);

		pageLabel = new JLabel("1/1");
		pageLabel.setBounds(370, 462, 80, 20);
		add(pageLabel);

		prev = new JButton("上一页");
		prev.setBounds(240, 462, 80, 20);
		prev.setFocusable(false);
		prev.setContentAreaFilled(false);
		add(prev);
		prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentpage > 1) {
					currentpage--;
					subList = songTable.updateCurrentPage(list, currentpage);
					pageLabel.setText(currentpage + "/" + pageSum);
				} else {
					System.out.println("已经是第一页了");
					new MessageDialog("已经是第一页了");
					currentpage = 1;// 防止越界
				}
			}
		});

		next = new JButton("下一页");
		next.setBounds(500, 462, 80, 20);
		next.setFocusable(false);
		next.setContentAreaFilled(false);
		add(next);
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentpage < pageSum) {
					currentpage++;
					subList = songTable.updateCurrentPage(list, currentpage);
					pageLabel.setText(currentpage + "/" + pageSum);
				} else {
					System.out.println("已经是最后一页了");
					new MessageDialog("已经是最后一页了");
					currentpage = pageSum;// 防止越界
				}
			}
		});
		
		jumpEdit = new JTextField("");
		jumpEdit.setBounds(415, 462, 20, 20);
		add(jumpEdit);
		jump = new JButton("go");
		jump.setBounds(440, 462, 52, 20);
		jump.setFocusable(false);
		jump.setContentAreaFilled(false);
		add(jump);
		jump.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentpage = Integer.valueOf(jumpEdit.getText());
				if (currentpage >= 1 && currentpage <= pageSum) {
					subList = songTable.updateCurrentPage(list, currentpage);
					pageLabel.setText(currentpage + "/" + pageSum);
				} else if (currentpage < 1) {
					currentpage = 1;
				} else if (currentpage > pageSum) {
					currentpage = pageSum;
				}
			}
		});
		
		songTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					initPopMenu(e);
				}
				if (e.getClickCount() == 2) {
					PlayManager.isUserClick = true;
					PlayManager.play(list, songTable, currentpage);
				}
			}

			private void initPopMenu(MouseEvent e) {
				JPopupMenu pop = new JPopupMenu();
				JMenuItem addTo = new JMenuItem("添加到");
				pop.add(addTo);
				pop.addSeparator();
				List<SongList> sheetList = DaoFactory.getSongListDao().listSongListName();
				for (SongList sl: sheetList) {
					JMenuItem sheet = new JMenuItem("<html>&nbsp;&nbsp;&nbsp;&nbsp;" + sl.getSlName() + "</html>");
					pop.add(sheet);
					sheet.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// System.out.println("点击了" + name);
							for (int i = 0; i < subList.size(); ++i) {
								if (songTable.getSelectedRow() == i) {
									// 查询出真实id
									int sId = DaoFactory.getSongDao().getSIdBySName( subList.get(i).getsName());
									if (DaoFactory.getSSLOperateDao().addToSongList(sl, sId)) {
										new MessageDialog("添加成功");
									} else {
										new MessageDialog("添加失败，已有这首歌曲");
									}
								}
							}
						}
					});
				}
				pop.addSeparator();
				JMenuItem remove = new JMenuItem("删除");
				pop.add(remove);
				pop.show(e.getComponent(), e.getX(), e.getY());
				addTo.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("点击了addto");
					}
				});
				remove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("sublist size=" + subList.size());
						for (int i = 0; i < subList.size(); ++i) {
							if (songTable.getSelectedRow() == i) {
								songTable.model.removeRow(i);
								songSum.setText("下载列表 " + --downloadListSum + "首");
							}
						}
					}
				});
			}
		});
	}

	private void initHeadInfo() {
		songSum = new JLabel("下载列表 0首");
		songSum.setBounds(20, 10, 125, 15);
		songSum.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		add(songSum);
	}

	private List<Song> formatSongInfo(List<String> filePathList) {
		List<Song> songList = new ArrayList<>();
		for (String path: filePathList) {
			songList.add(new Song(0, SongInfoProcessor.readSongName(path), 
					 SongInfoProcessor.readSinger(path), SongInfoProcessor.readSize(path), SongInfoProcessor.readDuration(path), path));
		}
		return songList;
	}

}
