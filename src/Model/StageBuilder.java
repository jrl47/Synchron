package Model;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Controller.MovementListener;
import View.Game;
import View.PixelArray;
import View.Sprite;
import View.SpriteSheet;

/**
 * 
 * @author Jacob
 * A class for constructing stages.
 */
public class StageBuilder {
	
	MovementListener listener;
	public StageBuilder(MovementListener lis) {
		listener = lis;
	}

	public Stage buildInitialStage(){
		BufferedImage image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		try {
			image = ImageIO.read(SpriteSheet.class.getResource("/level.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		PixelArray map = new PixelArray(new int[w*h], w);
		image.getRGB(0, 0, w, h, map.getPixels(), 0, w);
		
		SpriteSheet bg = new SpriteSheet("/amazingbackground.png", map.getWidth()*16, map.getHeight()*16);
		SpriteSheet sheet = new SpriteSheet("/lightdeciduousspritesheet.png", 80, 160);
		SpriteSheet red = new SpriteSheet("/redguy.png", 16, 16);
		Sprite back = new Sprite(map.getWidth()*16, map.getHeight()*16, 0, 0, bg);
		Sprite dirt = new Sprite(16, 16, 1, 1, sheet);
		Sprite redGuy = new Sprite(16, 16, 0, 0, red);
		Camera c1 = new Camera();
		Camera c2 = new Camera();
		List<Camera> cameras = new ArrayList<Camera>();
		cameras.add(c1); cameras.add(c2);
		Stage s = new Stage(map.getWidth()*16, map.getHeight()*16, 10, 10, cameras);
		Player p1 = new Player(98*16, 170*16, redGuy, s, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, listener);
		c1.addObject(p1);
		Player p2 = new Player(99*16, 170*16, redGuy, s, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, listener);
		c2.addObject(p2);
		s.addObject(p1);
		s.addObject(p2);
		s.addObject(new BackgroundObject(0, 0, back, s));
		for(int i=0; i<map.getWidth(); i++){
			for(int j=0; j<map.getHeight(); j++){
				if(map.getPixel(i, j)==-16777216){
					s.addObject(new Block(16*i, 16*j, dirt, s));
				}
			}
		}
		return s;
	}
	
}
