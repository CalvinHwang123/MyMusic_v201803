package calvin.baidumusic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * µ•¿˝Connection¿‡
 * @author Calvin
 *
 */
public class DBConnection {

	private static Connection conn = null;
	
	public static String URL = null;
	public static String DRIVER = null;
	
	private DBConnection() {}
	
	public static Connection getConnInstance() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL); 
		}
		return conn;
	}
}
