package calvin.baidumusic.dao;

public class DaoFactory {

	public static IUserDao getUserDao() {
		return new UserDaoImpl();
	}
	
	public static ISongDao getSongDao() {
		return new SongDaoImpl();
	}
	
	public static IFavoriteDao getFavoriteDao() {
		return new FavoriteDaoImpl();
	}
}
