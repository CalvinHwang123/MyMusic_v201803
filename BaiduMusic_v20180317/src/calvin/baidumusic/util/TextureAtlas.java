package calvin.baidumusic.util;

import java.awt.image.BufferedImage;
/**
 * 纹理图集类
 * 包含了同一图像对象的不同状态
 * @author Calvin
 *
 */
public class TextureAtlas {

	BufferedImage	image;

	public TextureAtlas(String imageName) {
		image = ResourceLoader.loadImage(imageName);
	}

	/**
	 * 获取子图像
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
