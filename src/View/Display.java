package View;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

import Model.Camera;
import Model.GameObject;

/**
 * 
 * @author Jacob
 * Takes GameObjects on a given stage and displays them.
 */
public class Display {
	
	private int width;
	private int height;
	private static final double WIDTH_SCALAR = .05;
	private static final double HEIGHT_SCALAR = .05;
	private Canvas canvas;
	private BufferedImage image;
	private PixelArray pixels;
	public Display(int w, int h, Canvas c){
		width = w;
		height = h;
		canvas = c;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int [] pixel = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		pixels = new PixelArray(pixel, width);
	}
	
	public void render(List<GameObject> list, List<Camera> cameras){
		Camera c1 = cameras.get(0);
		Camera c2 = cameras.get(1);
    	BufferStrategy bs = canvas.getBufferStrategy();
    	if(bs==null){
    		canvas.createBufferStrategy(3);
    		return;
    	}
    	
    	clear();
    	
    	// Sorts objects based on which are in front of one another
    	for(int i=0; i < list.size(); i++){
    		if(i+1 < list.size()){
    			if(list.get(i+1).getZ() < list.get(i).getZ()){
    				GameObject o1 = list.get(i);
    				GameObject o2 = list.get(i+1);
    				list.set(i, o2);
    				list.set(i+1, o1);
    			}
    		}
    	}
    	
    	// Draws objects in left screen
		for(int k=0; k < list.size(); k++){
			GameObject o = list.get(k);
			Sprite s = o.getSprite();
			for(int i = 0; i < s.XSIZE; i++){
				for(int j=0; j < s.YSIZE; j++){
					int locx = i + (int)o.getX() - c1.getX() + (int)WIDTH_SCALAR + (int)(((width/2)-2*WIDTH_SCALAR)/2);
					int locy = j + (int)o.getY()- c1.getY() + (int)(height-2*HEIGHT_SCALAR)/2;
					if(locx >=(width*WIDTH_SCALAR) && locx < (int)(width/2 - width*WIDTH_SCALAR) &&
							locy >=height*HEIGHT_SCALAR && locy < (int)(height - height*HEIGHT_SCALAR))
						pixels.setPixel(locx, locy, s.getPixels().getPixel(i,j));
				}
			}
		}
		
		// Draws objects in right screen
		for(int k=0; k < list.size(); k++){
			GameObject o = list.get(k);
			Sprite s = o.getSprite();
			for(int i = 0; i < s.XSIZE; i++){
				for(int j=0; j < s.YSIZE; j++){
					int locx = i + (int)o.getX() - c2.getX() + (int)WIDTH_SCALAR + width/2 + (int)(((width/2)-2*WIDTH_SCALAR)/2);
					int locy = j + (int)o.getY()- c2.getY() + (int)(height-2*HEIGHT_SCALAR)/2;
					if(locx >=(width*WIDTH_SCALAR)+(width/2) && locx < (int)(width - width*WIDTH_SCALAR) &&
							locy >=height*HEIGHT_SCALAR && locy < (int)(height - height*HEIGHT_SCALAR))
						pixels.setPixel(locx, locy, s.getPixels().getPixel(i,j));
				}
			}
		}
    	
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public void clear(){
		pixels.clear();
	}
	
}
