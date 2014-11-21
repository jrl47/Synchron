package View;

/**
 * 
 * @author Jacob
 * Keeps track of display information of GameObjects.
 */
public class Sprite {
	
	public final int XSIZE;
	public final int YSIZE;
	private int x;
	private int y;
	private PixelArray pixels;
	private SpriteSheet sheet;
	
	public Sprite(int xsize, int ysize, int xx, int yy, SpriteSheet s){
		XSIZE = xsize;
		YSIZE = ysize;
		pixels = new PixelArray(new int[xsize * ysize], xsize);
		x = xx*xsize;
		y = yy*ysize;
		sheet = s;
		loadPixels();
	}

	private void loadPixels() {
		for(int i=0; i < XSIZE; i++){
			for(int j=0; j< YSIZE; j++){
				pixels.setPixel(i,j,sheet.getPixels().getPixel(i+x,j+y));
			}
		}
	}
	
	public PixelArray getPixels(){
		return pixels;
	}

}
