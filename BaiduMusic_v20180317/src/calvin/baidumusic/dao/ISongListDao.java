package calvin.baidumusic.dao;

import java.util.List;

import calvin.baidumusic.bean.SongList;

public interface ISongListDao {
	boolean addSongList(String songListName);
	boolean renameSongList(String oldName, String newName);
	boolean deleteSongList(String songListName);
	List<SongList> listSongListName();
}
