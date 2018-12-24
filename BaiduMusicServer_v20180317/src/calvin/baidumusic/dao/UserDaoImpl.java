package calvin.baidumusic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calvin.baidumusic.bean.User;
import calvin.baidumusic.db.DBConnection;
/**
 * 用户DAO实现类
 * @author Calvin
 *
 */
public class UserDaoImpl implements IUserDao {

	@Override
	public boolean register(User u) {
		List<User> userList = listAllUser();
		for (User tempU: userList) {
			// 不允许同名用户存在
			if (tempU.getuName().equals(u.getuName())) {
				System.out.println("该用户已存在，请重新注册！");
				return false;
			}
		}
		
		String sql = "insert into bm_user(u_name, u_pwd) "
				+ "values(?, ?);";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getuName());
			pstmt.setString(2, u.getuPwd());
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("注册成功！");
				return true;
			} else {
				System.out.println("注册失败！");
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
	public User login(String uName, String uPwd) {
		String sql = "select * from bm_user where u_name = "
				+ "? and u_pwd = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uName);
			pstmt.setString(2, uPwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("登录成功！");
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
			} else {
				System.out.println("登录失败！不存在该用户或者密码错误！");
				return null;
			}
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
	public List<User> listAllUser() {
		List<User> userList = new ArrayList<>();
		String sql = "select * from bm_user;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			System.out.println("总共查询到用户 size = " + userList.size());
			return userList;
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
	public boolean resetPwd(User u) {
		String sql = "update bm_user set u_pwd = ? where u_name = ?;";
		PreparedStatement pstmt = null;
		try {
			Connection conn = DBConnection.getConnInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getuPwd());
			pstmt.setString(2, u.getuName());
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("重置密码成功！");
				return true;
			} else {
				System.out.println("重置密码失败！不存在该用户！");
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
