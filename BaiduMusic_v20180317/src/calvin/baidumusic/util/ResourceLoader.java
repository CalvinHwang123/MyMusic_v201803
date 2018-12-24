package calvin.baidumusic.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * ��Դ������
 * ���ڼ���BufferedImageͼƬ����
 * @author Calvin
 *
 */
public class ResourceLoader {

	public static final String	PATH	= "res/icon/";

	public static BufferedImage loadImage(String fileName) {

		BufferedImage image = null;

		try {

			image = ImageIO.read(new File(PATH + fileName));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;

	}

}
