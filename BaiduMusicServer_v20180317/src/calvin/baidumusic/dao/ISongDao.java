package calvin.baidumusic.dao;

import java.util.List;

import calvin.baidumusic.bean.Song;

public interface ISongDao {
	boolean addMultiSong(List<Song> list);
	boolean addSingleSong(Song song);
	List<Song> listAllSong();
	List<Song> listAllSongBySearch(String key);
	List<Song> listSheetAllSong(String songSheetName);
}
