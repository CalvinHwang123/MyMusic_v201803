package calvin.baidumusic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.db.DBConnection;

public class SongDaoImpl implements ISongDao {

	@Override
	public boolean addMultiSong(List<Song> list) {
		String sql = "insert into bm_song(s_name, s_singer, s_size, s_duration, s_path) "
				+ "values(?, ?, ?, ?, ?);";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); ++i) {
				Song s = list.get(i);
				pstmt.setString(1, s.getsName());
				pstmt.setString(2, s.getSinger());
				pstmt.setString(3, s.getSize());
				pstmt.setString(4, s.getsDuration());
				pstmt.setString(5, s.getsPath());
				try {
					int n = pstmt.executeUpdate();
					if (n > 0) {
						System.out.println("д���" + (i + 1) + "���������ݳɹ���");
					} else {
						System.out.println("д���" + (i + 1) + "����������ʧ�ܣ�");
					}
				} catch (Exception e) {
					System.out.println("���ɵ���ͬ������");
					return false;
				}
			}
			return true;
			
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
	public boolean addSingleSong(Song song) {
		
		String sql = "insert into bm_song(s_name, s_singer, s_size, s_duration, s_path) "
				+ "values(?, ?, ?, ?, ?);";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, song.getsName());
			pstmt.setString(2, song.getSinger());
			pstmt.setString(3, song.getSize());
			pstmt.setString(4, song.getsDuration());
			pstmt.setString(5, song.getsPath());
			try {
				int n = pstmt.executeUpdate();
				if (n > 0) {
					System.out.println("д���������ݳɹ���");
					return true;
				} else {
					System.out.println("д����������ʧ�ܣ�");
					return false;
				}
			} catch(Exception e) {
				System.out.println("����д��ͬ����������");
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
	public List<Song> listAllSong(int order) {
		List<Song> songList = new ArrayList<>();
		String sql = null;
		switch (order) {
		// ����������
		case 0:
			sql = "select * from bm_song order by s_name asc;";
			break;
		// ����������
		case 1:
			sql = "select * from bm_song order by s_name desc;";
			break;
		// ����������
		case 2:
			sql = "select * from bm_song order by s_singer asc;";
			break;
		// ����������
		case 3:
			sql = "select * from bm_song order by s_singer desc;";
			break;
		// ����С����
		case 4:
			sql = "select * from bm_song order by s_size asc;";
			break;
		// ����С����
		case 5:
			sql = "select * from bm_song order by s_size desc;";
			break;
		// ��ʱ������
		case 6:
			sql = "select * from bm_song order by s_duration asc;";
			break;
		// ��ʱ������
		case 7:
			sql = "select * from bm_song order by s_duration desc;";
			break;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				songList.add(new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			System.out.println("�ܹ���ѯ������ size = " + songList.size());
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

	@Override
	public List<Song> listSheetAllSong(String songSheetName) {
		List<Song> songList = new ArrayList<>();
		String sql = "select * from bm_song where s_id in(select s_id from bm_song_list where sl_name = ��);";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, songSheetName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				songList.add(new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			System.out.println(songSheetName + "�ܹ���ѯ������ size = " + songList.size());
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

	@Override
	public int getSIdBySName(String sName) {
		int sId = 0;
		String sql = "select s_id from bm_song where s_name = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sId = rs.getInt(1);
			}
			System.out.println("��ѯ��sName=" + sName + "��sIdΪ" + sId);
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
		return sId;
	}

	@Override
	public List<Song> listAllSongBySearch(String key) {
		List<Song> songList = new ArrayList<>();
		String sql = "select * from bm_song where s_name like '%" + key + "%' or s_singer like '%" + key + "%';";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, key);
//			pstmt.setString(2, key);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				songList.add(new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			System.out.println("�ؼ���Ϊ" + key + "�ܹ���ѯ������ size = " + songList.size());
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
