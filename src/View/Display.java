package View;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

import Model.BackgroundObject;
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
//	private int[] pixels;
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
	
	public void render(List<GameObject> list, Camera c){
    	BufferStrategy bs = canvas.getBufferStrategy();
    	if(bs==null){
    		canvas.createBufferStrategy(3);
    		return;
    	}
    	
    	clear();
    	for(int i=0; i < list.size(); i++){
    		if(!(list.get(i) instanceof BackgroundObject)){
    			if(i+1 < list.size()){
    				if(list.get(i+1) instanceof BackgroundObject){
    					GameObject o1 = list.get(i);
    					GameObject o2 = list.get(i+1);
    					list.set(i, o2);
    					list.set(i+1, o1);
    				}
    			}
    		}
    	}
		for(int k=0; k < list.size(); k++){
			GameObject o = list.get(k);
			Sprite s = o.getSprite();
			for(int i = 0; i < s.XSIZE; i++){
				for(int j=0; j < s.YSIZE; j++){
					int locx = i + (int)o.getX() - c.getX() + width/2;
					int locy = j + (int)o.getY()- c.getY() + height/2;
					if(locx >=0 && locx < width && locy >=0 && locy < height)
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
