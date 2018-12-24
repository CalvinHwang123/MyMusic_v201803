package calvin.baidumusic.bean;
/**
 * create table bm_song_list(
       sl_id integer primary key autoincrement,       
       sl_name varchar(255) not null
);
 * @author Calvin
 *
 */
public class SongList {
	
	private int slId;
	private String slName;
	
	public SongList(int slId, String slName) {
		this.slId = slId;
		this.slName = slName;
	}
	public int getSlId() {
		return slId;
	}
	public void setSlId(int slId) {
		this.slId = slId;
	}
	public String getSlName() {
		return slName;
	}
	public void setSlName(String slName) {
		this.slName = slName;
	}
	
	@Override
	public String toString() {
		return "SongList [slId=" + slId + ", slName=" + slName + "]";
	}
	
}
