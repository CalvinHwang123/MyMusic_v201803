package calvin.baidumusic.dao;

public class DaoFactory {

	public static ISongDao getSongDao() {
		return new SongDaoImpl();
	}
	
	public static ISongListDao getSongListDao() {
		return new SongListDaoImpl();
	}
	
	public static ISSLOperateDao getSSLOperateDao() {
		return new SSLOperateDaoImpl();
	}
}
