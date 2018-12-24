package calvin.baidumusic.dao;

import java.util.List;

import calvin.baidumusic.bean.Song;

public interface IFavoriteDao {
	boolean addFavorite(int uId, int musicId);
	boolean deleteFavorite(int uId, int musicId);
	List<Integer> listAllFavoriteSId(int uId);
	List<Song> listAllFavorite(int uId);
}
