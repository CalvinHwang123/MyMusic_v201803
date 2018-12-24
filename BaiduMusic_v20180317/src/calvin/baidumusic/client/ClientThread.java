package calvin.baidumusic.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.ImageIcon;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.bean.User;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.view.pane.MyCardPane;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * �ͻ����߳� ��Ҫ���������������ҵ�������ݵķ���
 * 
 * @author Calvin
 *
 */
public class ClientThread implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;

	private Socket client = null;
	private ClientStream clientStream = null;
	
	public Object[][] collectArr = null;
	
	public HeartThread ht = null;

	public ClientThread(Socket client) throws IOException {
		runFlag = true;
		this.client = client;
		clientStream = new ClientStream(client);
	}

	public ClientStream getClientStream() {
		return clientStream;
	}

	public void setClientStream(ClientStream clientStream) {
		this.clientStream = clientStream;
	}

	public void start() {
		th = new Thread(this);
		th.start();
	}

	public void resume() {
		runFlag = true;
	}

	public void suspend() {
		runFlag = false;
	}

	public void stop() throws IOException {
		runFlag = false;
		clientStream.getDis().close();
		clientStream.getDos().close();
		client.close();
	}

	@Override
	public void run() {
		while (runFlag) {
			try {
				String msg = clientStream.receive();
				handMsg(msg);
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("������������Ѿ��Ͽ����ر�socket����");
				try {
					stop();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("static-access")
	private void handMsg(String msg) {
		JSONObject jo = JSONObject.fromObject(msg);
		String type = jo.getString("type");
		switch (type) {
		case "register":
			if (jo.getInt("status") == 0) {
				new MessageDialog("ע��ʧ�ܣ��û����Ѵ��ڣ�");
				System.out.println("reg failed");
			} else {
				new MessageDialog("ע��ɹ���");
				System.out.println("reg success");
			}
			break;
		case "login":
			if (jo.getInt("status") == 0) {
				new MessageDialog("�û��������ڻ��������!");
				System.out.println("login faild");
			} else {
				new MessageDialog("��¼�ɹ�");
				PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).isLogin = true;
				System.out.println("login success");
				JSONObject userJo = jo.getJSONObject("user");
				PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).user = (User) userJo.toBean(userJo, User.class);
				PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).userLabel.setText("��ӭ," +
						PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).user.getuName());
				PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).loginAvatar.setIcon(new ImageIcon("res/icon/user.png"));
				PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).loginAvatar.setRolloverIcon((new ImageIcon("res/icon/user.png")));
				PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).loginAvatar.setPressedIcon((new ImageIcon("res/icon/user.png")));
				PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).exitLogin.setVisible(true);
				
				// ����������
				try {
					ht = new HeartThread(client);
					ht.start();
				} catch (IOException e) {
					e.printStackTrace();
				};
			}
			break;
		case "reset_pwd":
			if (jo.getInt("status") == 0) {
				new MessageDialog("��������ʧ��");
				System.out.println("reset faild");
			} else {
				new MessageDialog("��������ɹ�");
				System.out.println("reset success");
			}
			break;
		case "music":
			JSONArray array = jo.getJSONArray("song_list");
			@SuppressWarnings("unchecked")
			List<Song> list = (List<Song>) JSONArray.toCollection(array, Song.class);
			// ���������Ӧ
			if (msg.contains("search_str")) {
				updateSearchResultTable(list);
			} else {
				updateOnlineMusicTable(list);
			}
			break;
		case "collect":
			if (jo.getString("operate").equals("add")) {
				if (jo.getInt("status") == 0) {
					new MessageDialog("�ø����Ѿ��ղع���");
					System.out.println("collect faild");
				} else {
					new MessageDialog("�ղظ����ɹ�");
					System.out.println("collect success");
				}
			} else if (jo.getString("operate").equals("remove")){
				if (jo.getInt("status") == 0) {
					new MessageDialog("ȡ���ղ�ʧ��");
					System.out.println("remove collect faild");
				} else {
					new MessageDialog("ȡ���ղسɹ�");
					System.out.println("remove collect success");
				}
			}
			
			break;
		case "get_collect":
			if (jo.getInt("status") == 0) {
				
			} else {
				JSONArray array2 = jo.getJSONArray("song_list");
				@SuppressWarnings("unchecked")
				List<Song> collectList = (List<Song>) JSONArray.toCollection(array2, Song.class);
				updateFavoriteTable(collectList);
			}
			break;
		}

	}

	private void updateSearchResultTable(List<Song> list) {
		PaneManager.getSearchResultPaneInstance().list.addAll(list);
		int size = PaneManager.getSearchResultPaneInstance().list.size();
		System.out.println("search pane list size " + size);
		PaneManager.getSearchResultPaneInstance().songSum.setText("������� " + size + "��");
		PaneManager.getSearchResultPaneInstance().resultSum = size;
		PaneManager.getSearchResultPaneInstance().songTable.updateCurrentPage(PaneManager.getSearchResultPaneInstance().list, 1);
		PaneManager.getSearchResultPaneInstance().subList = PaneManager.getSearchResultPaneInstance().songTable.updateCurrentPage(PaneManager.getSearchResultPaneInstance().list, 1);
		PaneManager.getSearchResultPaneInstance().currentpage = 1;
		PaneManager.getSearchResultPaneInstance().pageSum = size % 10 == 0 ? size / 10 : size / 10 + 1;
		PaneManager.getSearchResultPaneInstance().pageLabel.setText("1/"+PaneManager.getSearchResultPaneInstance().pageSum);
		
		PaneManager.getMyCardPaneInstance().getCardLayout().show(PaneManager.getMyCardPaneInstance(), MyCardPane.SEARCHRESULTPANE);
	}

	private void updateFavoriteTable(List<Song> collectList) {
		PaneManager.getFavoritePaneInstance().songSum.setText("�ղ��б� " +collectList.size() + "��");
		PaneManager.getFavoritePaneInstance().favoriteSum = collectList.size();
		PaneManager.getFavoritePaneInstance().songTable.updateCurrentPage(collectList, 1);
		PaneManager.getFavoritePaneInstance().list = collectList;
		PaneManager.getFavoritePaneInstance().subList = PaneManager.getOnlineMusicPaneInstance().songTable.updateCurrentPage(collectList, 1);
		PaneManager.getFavoritePaneInstance().currentpage = 1;
		PaneManager.getFavoritePaneInstance().pageSum = collectList.size() % 10 == 0 ? collectList.size() / 10 : collectList.size() / 10 + 1;
		PaneManager.getFavoritePaneInstance().pageLabel.setText("1/"+PaneManager.getFavoritePaneInstance().pageSum);
	}

	private void updateOnlineMusicTable(List<Song> list) {
		PaneManager.getOnlineMusicPaneInstance().songSum.setText("�������� " +list.size() + "��");
		PaneManager.getOnlineMusicPaneInstance().songTable.updateCurrentPage(list, 1);
		PaneManager.getOnlineMusicPaneInstance().list = list;
		PaneManager.getOnlineMusicPaneInstance().subList = PaneManager.getOnlineMusicPaneInstance().songTable.updateCurrentPage(list, 1);
		PaneManager.getOnlineMusicPaneInstance().currentpage = 1;
		PaneManager.getOnlineMusicPaneInstance().pageSum = list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		PaneManager.getOnlineMusicPaneInstance().pageLabel.setText("1/"+PaneManager.getOnlineMusicPaneInstance().pageSum);
	}

}
