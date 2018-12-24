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
				System.out.println(songListName + "已存在，保存失败");
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
				System.out.println("保存" + songListName+ "成功！");
				return true;
			} else {
				System.out.println("保存" + songListName+ "失败！");
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
			System.out.println("总共查询到歌单数 size = " + songNameList.size());
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
				System.out.println("更改" + oldName+ "为" + newName + "成功！");
				return true;
			} else {
				System.out.println("更改" + oldName+ "为" + newName + "失败！");
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
				System.out.println("删除" + songListName+ "成功！");
				return true;
			} else {
				System.out.println("删除" + songListName+ "失败！");
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
