package calvin.baidumusic.dao;

import java.util.List;

import calvin.baidumusic.bean.User;

/**
 * UserDAO�ӿ�
 * �ṩע�ᣬ��¼�ӿڷ���
 * @author Calvin
 *
 */
public interface IUserDao {

	boolean register(User u);// ע��ӿڷ���
	User login(String uName, String uPwd);// ��¼�ӿڷ���
	List<User> listAllUser();// ��ȡ�����û��ӿڷ���
	boolean resetPwd(User u);// ��������ӿڷ���
}
