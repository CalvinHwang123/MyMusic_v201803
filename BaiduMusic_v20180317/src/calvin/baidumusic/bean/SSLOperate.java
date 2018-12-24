package calvin.baidumusic.bean;
/**
 * CREATE TABLE bm_song_song_list(
       ssl_id integer primary key autoincrement,       
       sl_id integer not null,       
       s_id integer not null
);
 * @author Calvin
 *
 */
public class SSLOperate {
	private int sslId;
	private int slId;
	private int sId;
	public SSLOperate(int sslId, int slId, int sId) {
		this.sslId = sslId;
		this.slId = slId;
		this.sId = sId;
	}
	public int getSslId() {
		return sslId;
	}
	public void setSslId(int sslId) {
		this.sslId = sslId;
	}
	public int getSlId() {
		return slId;
	}
	public void setSlId(int slId) {
		this.slId = slId;
	}
	public int getsId() {
		return sId;
	}
	public void setsId(int sId) {
		this.sId = sId;
	}
	@Override
	public String toString() {
		return "SSLOperate [sslId=" + sslId + ", slId=" + slId + ", sId=" + sId + "]";
	}
}
