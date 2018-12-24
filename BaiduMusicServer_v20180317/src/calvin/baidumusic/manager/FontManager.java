package calvin.baidumusic.manager;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * ���������
 * ��Ҫ��������ȫ������
 * @author Calvin
 *
 */
public class FontManager {
	/**
	 * ͳһ�������壬����������֮�������ɸ����������ӽ��涼����Ҫ�ٴ���������
	 */
	public static void initGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
}
