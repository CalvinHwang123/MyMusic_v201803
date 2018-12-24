package calvin.baidumusic.test;

import java.awt.Font;
import java.io.IOException;
import java.net.Socket;

import calvin.baidumusic.client.ClientThread;
import calvin.baidumusic.db.ConfigureFile;
import calvin.baidumusic.manager.FontManager;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.view.widget.dialog.MessageDialog;

public class MyTest {
	
	public static ClientThread ct = null;
	
	public static void main(String[] args) {
		
		ConfigureFile.readConfigInfo();
		FontManager.initGlobalFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		FrameManager.getMainFrameInstance();
		
		try {
			ct = new ClientThread(new Socket("localhost", 10000));
		} catch (IOException e) {
//			e.printStackTrace();
			if (ct == null) {
				new MessageDialog("·þÎñÆ÷Î´¿ªÆô");
				return;
			} 
		}
		if (ct != null) {
			ct.start();
		}
		
	}
}
