package calvin.baidumusic.util;

import java.awt.image.BufferedImage;
/**
 * ����ͼ����
 * ������ͬһͼ�����Ĳ�ͬ״̬
 * @author Calvin
 *
 */
public class TextureAtlas {

	BufferedImage	image;

	public TextureAtlas(String imageName) {
		image = ResourceLoader.loadImage(imageName);
	}

	/**
	 * ��ȡ��ͼ��
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public BufferedImage cut(int x, int y, int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

}
