package calvin.baidumusic.view.pane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.common.Constants;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.server.FileTransferServer;
import calvin.baidumusic.server.ServiceFunctionServer;
import calvin.baidumusic.util.SongInfoProcessor;
import calvin.baidumusic.view.widget.button.MyButton;
import calvin.baidumusic.view.widget.button.PlayButton;

public class MainPane extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final int width = 200;
	private final int height = 100;
	
	private PlayButton startBtn = null;
	private boolean isStart = false;
	public JLabel currentOnline = null;
	
	private MyButton minBtn  = null;
	private MyButton closeBtn  = null;
	
	private MyButton addSongBtn = null;
	public JLabel info = null;
	
	private JFrame frame = null;
	
	private JPopupMenu pop = null;
	private JMenuItem selectFile = null;
	private JMenuItem selectDirectory = null;
	
	private ServiceFunctionServer serviceFunctionServer = null;
	private FileTransferServer fileTransferServer = null;
	
	public MainPane(JFrame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(Constants.LOGO));
		logo.setBounds(8, 8, 82, 18);
		add(logo);
		
		startBtn = new PlayButton("启动服务器", "停止服务器", Constants.START_DEF, Constants.START_ROLLOVER, Constants.START_PRESSED, 50, 50);
		startBtn.setLocation(20, 24);
		add(startBtn);
		
		addSongBtn = new MyButton("导入歌曲", Constants.ADD_SONG, Constants.ADD_SONG_OVER, Constants.ADD_SONG_PRESS, 
				94, 36);
		addSongBtn.setLocation(100, 30);
		add(addSongBtn);
		
		info = new JLabel();
		info.setText("暂无信息" );
		info.setHorizontalAlignment(JLabel.CENTER);
		info.setForeground(Color.RED);
		info.setBounds(100, 75, 100, 15);
		info.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		add(info);
		
		currentOnline = new JLabel();
		currentOnline.setText("在线人数: 0" );
		currentOnline.setForeground(Color.WHITE);
		currentOnline.setBounds(10, 75, 100, 15);
		currentOnline.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		add(currentOnline);
		
		minBtn = new MyButton("最小化", Constants.MIN_DEF, Constants.MIN_ROLLOVER, Constants.MIN_PRESSED, 
				30, 30);
		minBtn.setLocation(140, 0);
		add(minBtn);
		closeBtn = new MyButton("关闭", Constants.CLOSE_DEF, Constants.CLOSE_ROLLOVER, Constants.CLOSE_PRESSED, 
				30, 30);
		closeBtn.setLocation(170, 0);
		add(closeBtn);
		
		startBtn.addActionListener(this);
		addSongBtn.addActionListener(this);
		minBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		
		pop = new JPopupMenu();
		selectFile = new JMenuItem("选择文件");
		selectDirectory = new JMenuItem("选择文件夹");
		pop.add(selectFile);
		pop.add(selectDirectory);
		add(pop);
		addSongBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pop.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		selectFile.addActionListener(this);
		selectDirectory.addActionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon(Constants.SERVER_BG).getImage(), 0, 0, 200, 100, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == minBtn) {
			frame.setExtendedState(JFrame.ICONIFIED);
		} else if (obj == closeBtn) {
			System.exit(0);
		} else if (obj == startBtn) {
			if (!isStart) {
				info.setText("服务器已启动");
				serviceFunctionServer = new ServiceFunctionServer();
				fileTransferServer = new FileTransferServer();
				serviceFunctionServer.start();
				fileTransferServer.start();
				isStart = true;
				startBtn.setPauseIcon();
			} else {
				info.setText("服务器已停止");
				try {
					serviceFunctionServer.stop();
					fileTransferServer.stop();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				isStart = false;
				startBtn.setPlayIcon();
			}
		} else if (obj == selectFile) {
			selectFile();
		} else if (obj == selectDirectory) {
			selectFiles();
		}
	}

	private void selectFile() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setMultiSelectionEnabled(false);
        int i = jFileChooser.showOpenDialog(null);
        if(i== JFileChooser.APPROVE_OPTION){ //打开文件
        	File file = jFileChooser.getSelectedFile();
        	List<String> pathList = new ArrayList<>();
        	System.out.println("file path " + file.getPath());
        	pathList.add(file.getPath());
        	List<Song> songList = formatSongInfo(pathList);
        	if (DaoFactory.getSongDao().addMultiSong(songList)) {
        		info.setText("导入单首歌曲");
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
        		info.setText("导入" + pathList.size() + "首歌曲");
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
