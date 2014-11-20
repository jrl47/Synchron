package View;

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
	private int[] pixels;
	
	public SpriteSheet(String p, int xsize, int ysize){
		path = p;
		XSIZE = xsize;
		YSIZE = ysize;
		pixels = new int[XSIZE*YSIZE];
		load();
	}
	
	private void load(){
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int[] getPixels(){
		return pixels;
	}

}
