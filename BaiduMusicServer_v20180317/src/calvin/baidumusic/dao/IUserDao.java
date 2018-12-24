package calvin.baidumusic.dao;

import java.util.List;

import calvin.baidumusic.bean.User;

/**
 * UserDAO接口
 * 提供注册，登录接口方法
 * @author Calvin
 *
 */
public interface IUserDao {

	boolean register(User u);// 注册接口方法
	User login(String uName, String uPwd);// 登录接口方法
	List<User> listAllUser();// 获取所有用户接口方法
	boolean resetPwd(User u);// 更新密码接口方法
}
