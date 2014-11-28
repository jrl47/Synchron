package Model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Controller.MovementListener;
import View.Game;
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
		SpriteSheet bg = new SpriteSheet("/amazingbackground.png", 800, 450);
		SpriteSheet sheet = new SpriteSheet("/lightdeciduousspritesheet.png", 80, 160);
		SpriteSheet red = new SpriteSheet("/redguy.png", 16, 16);
		Sprite back = new Sprite(800, 450, 0, 0, bg);
		Sprite dirt = new Sprite(16, 16, 1, 0, sheet);
		Sprite redGuy = new Sprite(16, 16, 0, 0, red);
		Camera c1 = new Camera();
		Camera c2 = new Camera();
		List<Camera> cameras = new ArrayList<Camera>();
		cameras.add(c1); cameras.add(c2);
		Stage s = new Stage(Game.width, Game.height, 10, 10, cameras);
		Player p1 = new Player(250, 225, redGuy, s, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, listener);
		c1.addObject(p1);
		Player p2 = new Player(300, 225, redGuy, s, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, listener);
		c2.addObject(p2);
		s.addObject(p1);
		s.addObject(p2);
		s.addObject(new BackgroundObject(0, 0, back, s));
		for(int i=1; i<41; i++){
			s.addObject(new Block(16*i, 300, dirt, s));
		}
		for(int i=1; i<20; i++){
			s.addObject(new Block(16, 300 - 16*i, dirt, s));
		}
		for(int i=1; i<20; i++){
			s.addObject(new Block(40*16, 300 - 16*i, dirt, s));
		}
		for(int i=1; i<41; i++){
			s.addObject(new Block(16*i, 300 - 16*20, dirt, s));
		}
		for(int i=30; i<35; i++){
			s.addObject(new Block(16*i, 245, dirt, s));
		}
		for(int i=25; i<30; i++){
			s.addObject(new Block(16*i, 200, dirt, s));
		}
		for(int i=15; i<20; i++){
			s.addObject(new Block(16*i, 159, dirt, s));
		}
		s.addObject(new Block(55, 340, dirt, s));
		return s;
	}
	
}
