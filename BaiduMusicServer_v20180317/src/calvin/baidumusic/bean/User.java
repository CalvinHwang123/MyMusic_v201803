package calvin.baidumusic.bean;
/**
 * 用户实体类
 * 
 * create table bm_user (
       u_id integer primary key autoincrement,       
       u_name varchar(255) not null,       
       u_pwd varchar(255) not null
);

 * 
 * @author Calvin
 *
 */
public class User {
	
	private int uId;
	private String uName;
	private String uPwd;
	
	public User() {}
	
	public User(String uName, String uPwd) {
		this.uName = uName;
		this.uPwd = uPwd;
	}
	
	public User(int uId, String uName, String uPwd) {
		this(uName, uPwd);
		this.uId = uId;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuPwd() {
		return uPwd;
	}

	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}

	@Override
	public String toString() {
		return "User [uId=" + uId + ", uName=" + uName + ", uPwd=" + uPwd + "]";
	}
	
	
}
