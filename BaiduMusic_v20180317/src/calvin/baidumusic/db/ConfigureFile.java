package calvin.baidumusic.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件类，
 * 用于从.properties文件中读取信息
 * @author Calvin
 *
 */
public class ConfigureFile {

	private static String Path = "db.properties";
	private static Properties p = new Properties();
	
	private static final String urlKey = "URL";
	private static final String driverKey = "DRIVER";
	
	static {
		try {
			p.load(new FileInputStream(Path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readConfigInfo() {
		DBConnection.URL = p.getProperty(urlKey);
		DBConnection.DRIVER = p.getProperty(driverKey);
	}
}
