package calvin.baidumusic.bean;
/**
 * 收藏歌单实体类
 * @author Calvin
 *
 * create table bm_favorite(
       f_id integer primary key autoincrement,       
       s_id integer not null,       
       u_id integer not null,       
       foreign key(s_id) references bm_song(s_id),       
       foreign key(u_id) references bm_user(u_id)
);
 *
 */
public class Favorite {

	private int fId;
	private int sId;
	private int uId;
	
	public Favorite(int fId, int sId, int uId) {
		super();
		this.fId = fId;
		this.sId = sId;
		this.uId = uId;
	}

	public int getfId() {
		return fId;
	}

	public void setfId(int fId) {
		this.fId = fId;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	@Override
	public String toString() {
		return "Favorite [fId=" + fId + ", sId=" + sId + ", uId=" + uId + "]";
	}
	
	
}
