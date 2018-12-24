package calvin.baidumusic.view.pane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
import calvin.baidumusic.client.FileDownloadThread;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.manager.PlayManager;
import calvin.baidumusic.test.MyTest;
import calvin.baidumusic.util.TimeUtil;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import calvin.baidumusic.view.widget.table.MyTable;
import javazoom.jl.player.MyPlayer;
import net.sf.json.JSONObject;
/**
 * �ղص������
 * ��Ҫ��ʾ�û��ղص������б�
 * @author Calvin
 *
 */
public class FavoritePane extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 825;
	private final int HEIGHT = 492;
	
	public JLabel songSum = null;
	public int favoriteSum = 0;
	
	private static final Object[] colData = {0, "����", "����", "��С", "ʱ��", "·��"};
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
	
	public FavoritePane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		
		initHeadInfo();
		initTable();
		
	}

	private void initTable() {
		// �տ�ʼDefaultTableModelû������
		songTable = new MyTable(new DefaultTableModel(colData, 0), WIDTH, 415, this);
		add(songTable);

		pageLabel = new JLabel("1/1");
		pageLabel.setBounds(370, 462, 80, 20);
		add(pageLabel);

		prev = new JButton("��һҳ");
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
					System.out.println("�Ѿ��ǵ�һҳ��");
					new MessageDialog("�Ѿ��ǵ�һҳ��");
					currentpage = 1;// ��ֹԽ��
				}
			}
		});

		next = new JButton("��һҳ");
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
					System.out.println("�Ѿ������һҳ��");
					new MessageDialog("�Ѿ������һҳ��");
					currentpage = pageSum;// ��ֹԽ��
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
					PlayManager.isOnline = true;
					requestDownload(subList);
				}
			}
		});
	}
	
	public void requestDownload(List<Song> subList) {
		Song s = null;
		// ���������ص������б�
		for (int i = 0; i < subList.size(); ++i) {
			s = subList.get(i);
			if (songTable.getSelectedRow() == i) {
				requestDownload(s.getsPath());
				break;
			}
		}
	}
	
	public void playFromDownloadList(String name) {
		PlayManager.isOnline = true;
		File file = new File("res/download");
		File[] files = file.listFiles();
		for (File temp : files) {
			String path = temp.getAbsolutePath();
			if (path.contains(name)) {
				System.out.println("����·�� " + path);
				if (PlayManager.mp != null && PlayManager.mp.mt.isAlive()) {
					PlayManager.mp.stop();
				}
				PlayManager.mp = new MyPlayer(path, PlayManager.listener);
				PlayManager.mp.play();
				String totalTime = TimeUtil.formatTime((long) PlayManager.mp.getTotalMs());
				String nameAndSinger = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".mp3"));
				PlayManager.refreshMusicOperatePane(totalTime, nameAndSinger);
				break;
			}
		}
	}
	
	private void initPopMenu(MouseEvent e) {
		JPopupMenu pop = new JPopupMenu();
		JMenuItem download = new JMenuItem("����");
		JMenuItem remove = new JMenuItem("ɾ��");
		pop.add(download);
		pop.add(remove);
		pop.show(e.getComponent(), e.getX(), e.getY());
		download.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < subList.size(); ++i) {
					Song s = subList.get(i);
					if (songTable.getSelectedRow() == i) {
						requestDownload(s.getsPath());
					}
				}
			}
		});
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("sublist size=" + subList.size());
				for (int i = 0; i < subList.size(); ++i) {
					if (songTable.getSelectedRow() == i) {
						songTable.model.removeRow(i);
						songSum.setText("�ղ��б� " + --favoriteSum + "��");
						// ��������ݿ���ɾ��
						requestRemoveFavorite(subList.get(i).getsId());
					}
				}
			}
		});
	}

	private void requestDownload(String path) {
		String fileName = path.substring(path.lastIndexOf("\\") + 1);
		// �����ظ�����
		File dir = new File(FileDownloadThread.filePrex);
		if (dir.exists()) {
			File[] f = dir.listFiles();
			for (File temp : f) {
				if (fileName.equals(temp.getName())) {
					 new MessageDialog(fileName.substring(0, fileName.lastIndexOf(".mp3")) + " �Ѵ���");
					 if (PlayManager.isOnline) {
						 // �ļ��Ѵ��������б�ֱ�Ӳ���
						 playFromDownloadList(fileName);
						 PlayManager.isOnline = false;
					 }
					return;
				}
			}
		}
		// �����ļ������߳�
		Socket downloadSocket = null;
		FileDownloadThread fdt = null;
		try {
			downloadSocket = new Socket("localhost", 10001);
			fdt = new FileDownloadThread(downloadSocket);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (downloadSocket != null
				&& fdt != null) {
			fdt.start();
			JSONObject jo = new JSONObject();
			jo.put("type", "download");
			jo.put("path", path);
			try {
				fdt.getClientStream().send(jo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void requestRemoveFavorite(int sId) {
		JSONObject jo = new JSONObject();
		jo.put("type", "collect");
		jo.put("user_id", PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).user.getuId());
		jo.put("music_id", sId);
		jo.put("operate", "remove");
		try {
			MyTest.ct.getClientStream().send(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initHeadInfo() {
		songSum = new JLabel("�ղ��б� 0��");
		songSum.setBounds(20, 10, 125, 15);
		songSum.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		add(songSum);
	}

}
