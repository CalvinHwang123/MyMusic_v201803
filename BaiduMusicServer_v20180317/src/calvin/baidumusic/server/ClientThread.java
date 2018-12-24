package calvin.baidumusic.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.bean.User;
import calvin.baidumusic.dao.DaoFactory;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * �������ͻ����߳�
 * ��Ҫ���ڴ���ͻ��˷�����ҵ����������Ϣ
 * @author Calvin
 *
 */
public class ClientThread implements Runnable {

	private volatile boolean runFlag = false;
	private Thread th = null;
	
	private Socket client = null;
	private ServerStream serverStream = null;
	
	public static Map<String, ClientThread> userMap = new HashMap<>();
	public static int onlineSum = 0;
	public int counter = 0;
	private User user = null;
	
	public ClientThread(Socket client) throws IOException {
		runFlag = true;
		this.client = client;
		serverStream = new ServerStream(client);
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
		serverStream.getDis().close();
		serverStream.getDos().close();
		client.close();
	}
	
	@Override
	public void run() {
		while (runFlag) {
			try {
				String msg = serverStream.receive();
				handMsg(msg);
			} catch (IOException e) {
//				e.printStackTrace();
				if (user != null) {
					System.out.println("�Ѿ���ʼ�ղ���" + user.getuName() + "��������");
				}
			}
		}
	}

	private void handMsg(String msg) {
		JSONObject jo = JSONObject.fromObject(msg);
		String type = jo.getString("type");
		switch (type) {
		case "register":
			JSONObject userJo = jo.getJSONObject("user");
			User u = (User) JSONObject.toBean(userJo, User.class);
			JSONObject jo2 = new JSONObject();
			jo2.put("type", "register");
			if (DaoFactory.getUserDao().register(u)) {
				// ע��ɹ�
				jo2.put("status", 1);
			} else {
				// ע��ʧ��
				jo2.put("status", 0);
			}
			try {
				serverStream.send(jo2.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "login":
			JSONObject userJo2 = jo.getJSONObject("user");
			User u2 = (User) JSONObject.toBean(userJo2, User.class);
			JSONObject jo3 = new JSONObject();
			jo3.put("type", "login");
			user = DaoFactory.getUserDao().login(u2.getuName(), u2.getuPwd());
			if (user != null) {
				// ��¼�ɹ�
				jo3.put("status", 1);
				jo3.put("user", user);
				// �����û���Ϣ
				ClientThread.userMap.put(user.getuName(), this);
				// ���·�����������Ϣ
				PaneManager.getMainPaneInstance(FrameManager.getMainFrameInstance()).currentOnline.setText("����������" + ++onlineSum);
				PaneManager.getMainPaneInstance(FrameManager.getMainFrameInstance()).info.setText(user.getuName() + "��¼�ɹ�");;
			} else {
				// ��¼ʧ��
				jo3.put("status", 0);
			}
			try {
				serverStream.send(jo3.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "reset_pwd":
			JSONObject userJo3 = jo.getJSONObject("user");
			User u4 = (User) JSONObject.toBean(userJo3, User.class);
			JSONObject jo4 = new JSONObject();
			jo4.put("type", "reset_pwd");
			if (DaoFactory.getUserDao().resetPwd(u4)) {
				// ��������ɹ�
				jo4.put("status", 1);
			} else {
				// ��������ʧ��
				jo4.put("status", 0);
			}
			try {
				serverStream.send(jo4.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "music":
			List<Song> list = null;
			JSONObject musicObj = new JSONObject();
			// ��������
			if (msg.contains("search_str")) {
				list = DaoFactory.getSongDao().listAllSongBySearch(jo.getString("search_str"));
				musicObj.put("search_str", jo.getString("search_str"));
			// ��ȡ������������
			} else {
				list = DaoFactory.getSongDao().listAllSong();
			}
			JSONArray arr = JSONArray.fromObject(list);
			musicObj.put("type", "music");
			musicObj.put("song_list", arr);
			System.out.println("musicObj=" + musicObj.toString());
			try {
				serverStream.send(musicObj.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "collect":
			String operate = jo.getString("operate");
			JSONObject collectObj = new JSONObject();
			collectObj.put("type", "collect");
			if (operate.equals("add")) {
				collectObj.put("operate", "add");
				if (DaoFactory.getFavoriteDao().addFavorite(jo.getInt("user_id"), jo.getInt("music_id"))) {
					collectObj.put("status", 1);
					try {
						serverStream.send(collectObj.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					collectObj.put("status", 0);
					try {
						serverStream.send(collectObj.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (operate.equals("remove")) {
				collectObj.put("operate", "remove");
				if (DaoFactory.getFavoriteDao().deleteFavorite(jo.getInt("user_id"), jo.getInt("music_id"))) {
					collectObj.put("status", 1);
					try {
						serverStream.send(collectObj.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					collectObj.put("status", 0);
					try {
						serverStream.send(collectObj.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		case "get_collect":
			List<Song> allFavorite = DaoFactory.getFavoriteDao().listAllFavorite(jo.getInt("user_id"));
			JSONObject getCollectObj = new JSONObject();
			getCollectObj.put("type", "get_collect");
			JSONArray getCollArr = JSONArray.fromObject(allFavorite);
			if (allFavorite != null && allFavorite.size() > 0) {
				getCollectObj.put("status", 1);
				getCollectObj.put("song_list", getCollArr);
				try {
					serverStream.send(getCollectObj.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				getCollectObj.put("status", 0);
				try {
					serverStream.send(getCollectObj.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case "heart":
			String userName = jo.getString("user_name");
			// �յ�����������������
			counter = 0;
			System.out.println("��" + userName + "�ļ���������");
			break;
		}		
	}

}
