package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Jacob
 * Contains information necessary for Sprite display.
 */
public class SpriteSheet {
	
	private String path;
	public final int XSIZE;
	public final int YSIZE;
	private PixelArray pixels;
	
	public SpriteSheet(String p, int xsize, int ysize){
		path = p;
		XSIZE = xsize;
		YSIZE = ysize;
		pixels = new PixelArray(new int[XSIZE*YSIZE], XSIZE);
		load();
	}
	
	private void load(){
		try {
			BufferedImage origImage = ImageIO.read(SpriteSheet.class.getResource(path));
			BufferedImage image = new BufferedImage(XSIZE, YSIZE, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(origImage, 0, 0, XSIZE, YSIZE, null);
			g.dispose();
			int w = XSIZE;
			int h = YSIZE;
//			int w = image.getWidth();
//			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels.getPixels(), 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public PixelArray getPixels(){
		return pixels;
	}

}
