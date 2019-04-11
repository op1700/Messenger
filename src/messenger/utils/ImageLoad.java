package messenger.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageLoad {
	BufferedImage img = null;

	// �̹��� �ҷ�����
	public Image getImage(String filename) {

		URL url = this.getClass().getClassLoader().getResource(filename);
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}
}