package calvin.baidumusic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.bean.SongList;
import calvin.baidumusic.db.DBConnection;

public class SSLOperateDaoImpl implements ISSLOperateDao {

	@Override
	public boolean addToSongList(SongList sl, int sId) {
		// �������ظ����
		List<Song> allSong = DaoFactory.getSSLOperateDao().listAllSong(sl.getSlName());
		for (Song s: allSong) {
			if (s.getsId() == sId) {
				System.out.println("��ӵ�" + sl.getSlName() + "ʧ�ܣ��������׸���");
				return false;
			}
		}
		String sql = "insert into bm_song_song_list(sl_id, s_id) values(?, ?);";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, sl.getSlId());
			pstmt.setInt(2, sId);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("����" + sId +"��" + sl.getSlName() + "�ɹ���");
				return true;
			} else {
				System.out.println("����" + sId +"��" + sl.getSlName() + "ʧ�ܣ�");
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	@Override
	public boolean deleteFromSongList(int slId, int sId) {
		String sql = "delete from bm_song_song_list where sl_id = ? and s_id = ?;";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, slId);
			pstmt.setInt(2, sId);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("��" + slId + "ɾ��" + sId + "�ɹ���");
				return true;
			} else {
				System.out.println("��" + slId + "ɾ��" + sId + "ʧ�ܣ�");
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	@Override
	public List<Song> listAllSong(String slName) {
		List<Song> songList = new ArrayList<>();
		String sql = "select * from bm_song where s_id in (select s_id from bm_song_song_list where sl_id = (select sl_id from bm_song_list where sl_name = ?));";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, slName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				songList.add(new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			System.out.println(slName + "�ܹ���ѯ�������� size = " + songList.size());
			return songList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

}
