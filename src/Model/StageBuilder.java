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
		// change 100s to be robust
		PixelArray map = new PixelArray(new int[100*100], 100);
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource("/level2.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, map.getPixels(), 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SpriteSheet bg = new SpriteSheet("/amazingbackground.png", 1700, 1700);
		SpriteSheet sheet = new SpriteSheet("/lightdeciduousspritesheet.png", 80, 160);
		SpriteSheet red = new SpriteSheet("/redguy.png", 16, 16);
		Sprite back = new Sprite(1700, 1700, 0, 0, bg);
		Sprite dirt = new Sprite(16, 16, 1, 0, sheet);
		Sprite redGuy = new Sprite(16, 16, 0, 0, red);
		Camera c1 = new Camera();
		Camera c2 = new Camera();
		List<Camera> cameras = new ArrayList<Camera>();
		cameras.add(c1); cameras.add(c2);
		Stage s = new Stage(1700, 1700, 10, 10, cameras);
		Player p1 = new Player(48*16, 93*16, redGuy, s, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, listener);
		c1.addObject(p1);
		Player p2 = new Player(51*16, 93*16, redGuy, s, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, listener);
		c2.addObject(p2);
		s.addObject(p1);
		s.addObject(p2);
		s.addObject(new BackgroundObject(0, 0, back, s));
		for(int i=0; i<100; i++){
			for(int j=0; j<100; j++){
				if(map.getPixel(i, j)==-16777216){
					s.addObject(new Block(16*i, 16*j, dirt, s));
				}
			}
		}
		
//		for(int i=0; i<map.getWidth(); i++){
//			for(int j=0; j<map.getHeight(); j++){
//				if(map.getPixel(i, j)!=0){
//					s.addObject(new Block(16*i, 16*j, dirt, s));
//				}
//			}
//		}
//		for(int i=1; i<41; i++){
//			s.addObject(new Block(16*i, 300, dirt, s));
//		}
//		for(int i=1; i<20; i++){
//			s.addObject(new Block(16, 300 - 16*i, dirt, s));
//		}
//		for(int i=1; i<20; i++){
//			s.addObject(new Block(40*16, 300 - 16*i, dirt, s));
//		}
//		for(int i=1; i<41; i++){
//			s.addObject(new Block(16*i, 300 - 16*20, dirt, s));
//		}
//		for(int i=30; i<35; i++){
//			s.addObject(new Block(16*i, 245, dirt, s));
//		}
//		for(int i=25; i<30; i++){
//			s.addObject(new Block(16*i, 200, dirt, s));
//		}
//		for(int i=15; i<20; i++){
//			s.addObject(new Block(16*i, 159, dirt, s));
//		}
//		s.addObject(new Block(55, 340, dirt, s));
		return s;
	}
	
}
