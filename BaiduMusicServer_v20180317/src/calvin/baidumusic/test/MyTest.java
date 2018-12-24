package calvin.baidumusic.test;

import calvin.baidumusic.db.ConfigureFile;
import calvin.baidumusic.manager.FrameManager;

public class MyTest {
	public static void main(String[] args) {
		ConfigureFile.readConfigInfo();
		FrameManager.getMainFrameInstance();
	}
}
