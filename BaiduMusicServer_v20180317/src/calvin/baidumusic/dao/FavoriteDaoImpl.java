package calvin.baidumusic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.db.DBConnection;

public class FavoriteDaoImpl implements IFavoriteDao {

	@Override
	public boolean addFavorite(int uId, int musicId) {
		List<Integer> songIdList = listAllFavoriteSId(uId);
		for (Integer songId: songIdList) {
			// 不允许收藏同一首歌
			if (songId.equals(musicId)) {
				System.out.println("该歌曲已经收藏过了！");
				return false;
			}
		}
		
		String sql = "insert into bm_favorite(s_id, u_id) "
				+ "values(?, ?);";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, musicId);
			pstmt.setInt(2, uId);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("收藏歌曲成功！");
				return true;
			} else {
				System.out.println("收藏歌曲失败！");
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
	public boolean deleteFavorite(int uId, int musicId) {
		String sql = "delete from bm_favorite where u_id = ? and s_id = ?;";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uId);
			pstmt.setInt(2, musicId);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("删除收藏歌曲成功！");
				return true;
			} else {
				System.out.println("删除收藏歌曲失败！");
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
	public List<Integer> listAllFavoriteSId(int uId) {
		List<Integer> songList = new ArrayList<>();
		String sql = "select s_id from bm_favorite where u_id = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				songList.add(rs.getInt(1));
			}
			System.out.println("uid=" + uId + "收藏歌曲id size " + songList.size());
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
	public List<Song> listAllFavorite(int uId) {
		List<Song> songList = new ArrayList<>();
		String sql = "select s.* from bm_song s where s.s_id in(select s_id from bm_favorite where u_id = ?);";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				songList.add(new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			System.out.println("uid=" + uId + "收藏歌曲 size " + songList.size());
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
