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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.bean.SongList;
import calvin.baidumusic.common.Constants;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.PlayManager;
import calvin.baidumusic.util.SongInfoProcessor;
import calvin.baidumusic.view.widget.button.MyButton;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import calvin.baidumusic.view.widget.popmenu.MyPopMenu;
import calvin.baidumusic.view.widget.table.MyTable;
/**
 * 本地音乐面板
 * 主要显示本地音乐列表（使用JTable）
 * @author Calvin
 *
 */
public class LocalMusicPane extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final int WIDTH = 825;
	private final int HEIGHT = 492;
	
	private MyButton addLocal = null;
	private MyButton sort = null;
	public JLabel songSum = null;
	public int localMusicSum = 0;
	private static final Object[] colData = {0, "歌曲", "歌手", "大小", "时间", "路径"};
	public MyTable songTable = null;
	
	private JButton prev = null;
	private JButton next = null;
	private JButton jump = null;
	private JTextField jumpEdit = null;
	
	public List<Song> list = new ArrayList<>();
	public List<Song> subList = new ArrayList<>();
	
	public int currentpage = 1;
	public int pageSum = 0;
	public JLabel pageLabel = null;
	
	private JPopupMenu pop = null;
	private JMenuItem selectFile = null;
	private JMenuItem selectDirectory = null;
	
	public LocalMusicPane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		
		initHeadInfo();
		initTableInfo();
		initLocalDatabaseMusic();
		
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
									if (DaoFactory.getSSLOperateDao().addToSongList(sl, subList.get(i).getsId())) {
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
								songSum.setText("本地音乐 " + --localMusicSum + "首");
							}
						}
					}
				});
			}
		});
	}

	public void initLocalDatabaseMusic() {
		list = DaoFactory.getSongDao().listAllSong(0);
		subList = songTable.updateCurrentPage(list, 1);
		pageSum = list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		if (list != null && list.size() > 0) {
			songTable.updateCurrentPage(list, 1);
			songSum.setText("本地音乐 " + list.size() + "首");
			localMusicSum = list.size();
			pageLabel.setText("1/" + pageSum);
		}
	}


	private void initHeadInfo() {
		addLocal = new MyButton(null, Constants.ADD_LOCAL, Constants.ADD_LOCAL2, Constants.ADD_LOCAL3, 94, 36);
		addLocal.setLocation(650, 3);
		add(addLocal);
		
		sort = new MyButton(null, Constants.SORT, Constants.SORT2, Constants.SORT3, 71, 36);
		sort.setLocation(748, 1);
		add(sort);
		sort.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new MyPopMenu().show(e);
			}
		});
		
		songSum = new JLabel("本地音乐 0首");
		songSum.setBounds(20, 10, 125, 15);
		songSum.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		add(songSum);
		
		pop = new JPopupMenu();
		selectFile = new JMenuItem("选择文件");
		selectDirectory = new JMenuItem("选择文件夹");
		pop.add(selectFile);
		pop.add(selectDirectory);
		add(pop);
		
		addLocal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pop.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		selectFile.addActionListener(this);
		selectDirectory.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == selectFile) {
			selectFile();
			initLocalDatabaseMusic();
		} else if (obj == selectDirectory) {
			selectFiles();
			initLocalDatabaseMusic();
		}
	}
	
	private void selectFile() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setMultiSelectionEnabled(false);
        int i = jFileChooser.showOpenDialog(null);
        if(i== JFileChooser.APPROVE_OPTION){ //打开文件
        	File file = jFileChooser.getSelectedFile();
        	List<String> pathList = new ArrayList<>();
        	pathList.add(file.getPath());
        	List<Song> songList = formatSongInfo(pathList);
        	if (DaoFactory.getSongDao().addMultiSong(songList)) {
        		System.out.println("导入单首歌曲");
        	}
        }else{
            System.out.println("没有选中文件");
        }
	}

	private void selectFiles() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = jFileChooser.showOpenDialog(null);
        if(i== JFileChooser.APPROVE_OPTION){ //打开文件
        	File file = jFileChooser.getSelectedFile();
        	List<String> pathList = new ArrayList<>();
        	search(file.getPath(), ".mp3", pathList);
        	List<Song> songList = formatSongInfo(pathList);
        	if (DaoFactory.getSongDao().addMultiSong(songList)) {
        		System.out.println("导入" + pathList.size() + "首歌曲");
        	}
        }else{
            System.out.println("没有选中文件夹");
        }
	}
	
	private void search(String dir, String type, List<String> list) {
		File file = new File(dir);
		if(file.exists()){
			File[] files = file.listFiles();
			for(File temp: files){
				if(temp.isDirectory()){
					//递归查找
					search(temp.getPath(), type, list);
				}else{
					if (temp.getName().endsWith(type)) {
						list.add(temp.getPath());
					}
				}
			}
		}
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
