package calvin.baidumusic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calvin.baidumusic.bean.SongList;
import calvin.baidumusic.db.DBConnection;

public class SongListDaoImpl implements ISongListDao {

	@Override
	public boolean addSongList(String songListName) {
		List<SongList> songList = DaoFactory.getSongListDao().listSongListName();
		for (SongList sl: songList) {
			if (sl.getSlName().equals(songListName)) {
				System.out.println(songListName + "�Ѵ��ڣ�����ʧ��");
				return false;
			}
		}
		String sql = "insert into bm_song_list(sl_name) "
				+ "values(?);";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, songListName);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("����" + songListName+ "�ɹ���");
				return true;
			} else {
				System.out.println("����" + songListName+ "ʧ�ܣ�");
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
	public List<SongList> listSongListName() {
		List<SongList> songNameList = new ArrayList<>();
		String sql = "select * from bm_song_list;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				songNameList.add(new SongList(rs.getInt(1), rs.getString(2)));
			}
			System.out.println("�ܹ���ѯ���赥�� size = " + songNameList.size());
			return songNameList;
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

	@Override
	public boolean renameSongList(String oldName, String newName) {
		String sql = "update bm_song_list set sl_name = ? where sl_name = ?;";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newName);
			pstmt.setString(2, oldName);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("����" + oldName+ "Ϊ" + newName + "�ɹ���");
				return true;
			} else {
				System.out.println("����" + oldName+ "Ϊ" + newName + "ʧ�ܣ�");
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
	public boolean deleteSongList(String songListName) {
		String sql = "delete from bm_song_list where sl_name = ?;";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, songListName);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("ɾ��" + songListName+ "�ɹ���");
				return true;
			} else {
				System.out.println("ɾ��" + songListName+ "ʧ�ܣ�");
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

}
