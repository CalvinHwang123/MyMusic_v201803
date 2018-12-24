package calvin.baidumusic.dao;

import java.util.List;

import calvin.baidumusic.bean.Song;
import calvin.baidumusic.bean.SongList;

public interface ISSLOperateDao {
	boolean addToSongList(SongList sl, int sId);
	boolean deleteFromSongList(int slId, int sId);
	List<Song> listAllSong(String slName);
}
