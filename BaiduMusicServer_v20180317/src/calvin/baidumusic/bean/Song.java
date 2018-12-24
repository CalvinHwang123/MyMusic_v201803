package calvin.baidumusic.bean;
/**
 * ∏Ë«˙ µÃÂ¿‡
 * 
 * create table bm_song (
       s_id integer primary key autoincrement,       
       s_name varchar(255) not null,       
       s_singer varchar(255) not null,  
       s_size varchar(255) not null,
       s_duration varchar(255) not null,     
       s_path varchar(255) not null
);
 * 
 * @author Calvin
 *
 */
public class Song {

	private int sId;
	private String sName;
	private String singer;
	private String size;
	private String sDuration;
	private String sPath;
	
	public Song() {}
	
	public Song(int sId, String sName, String singer, String size, String sDuration, String sPath) {
		this.sId = sId;
		this.sName = sName;
		this.singer = singer;
		this.size = size;
		this.sDuration = sDuration;
		this.sPath = sPath;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getsPath() {
		return sPath;
	}

	public void setsPath(String sPath) {
		this.sPath = sPath;
	}

	public String getsDuration() {
		return sDuration;
	}

	public void setsDuration(String sDuration) {
		this.sDuration = sDuration;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Song [sId=" + sId + ", sName=" + sName + ", singer=" + singer + ", size=" + size + ", sDuration="
				+ sDuration + ", sPath=" + sPath + "]";
	}
	
}
