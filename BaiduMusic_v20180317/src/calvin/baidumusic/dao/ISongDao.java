package calvin.baidumusic.dao;

import java.util.List;

import calvin.baidumusic.bean.Song;

public interface ISongDao {
	boolean addMultiSong(List<Song> list);
	boolean addSingleSong(Song song);
	int getSIdBySName(String sName);
	List<Song> listAllSong(int sort);
	List<Song> listSheetAllSong(String songSheetName);
	List<Song> listAllSongBySearch(String key);
}
