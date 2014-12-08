package Model;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Controller.GravityForcable;
import Controller.InputUser;
import Controller.MovementListener;
import View.PixelArray;
import View.Sprite;
import View.SpriteSheet;

/**
 * 
 * @author Jacob
 * An area for objects to exist and interact on.
 */
public class Stage {
	private int width;
	private int height;
	private static final int XTILES = 10;
	private static final int YTILES = 10;
	TileGrid myGrid;
	private List<Camera> myCameras;
	private List<GameObject> myObjects;
	private MovementListener listener;
	public Stage(MovementListener lis){
		listener = lis;
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
		
		width = map.getWidth()*16;
		height = map.getHeight()*16;
		myGrid = new TileGrid(XTILES, YTILES);
		myObjects = new ArrayList<GameObject>();
		
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
		Player p1 = new Player(98*16, 170*16, redGuy, this, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, listener);
		c1.addObject(p1);
		Player p2 = new Player(99*16, 170*16, redGuy, this, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, listener);
		c2.addObject(p2);
		myCameras = cameras;
		addObject(p1);
		addObject(p2);
		addObject(new GameObject(0, 0, back, this, 0));
		for(int i=0; i<map.getWidth(); i++){
			for(int j=0; j<map.getHeight(); j++){
				if(map.getPixel(i, j)==-16777216){
					addObject(new Block(16*i, 16*j, dirt, this));
				}
			}
		}
	}
	public void addObject(GameObject o){
		myObjects.add(o);
	}
	public List<GameObject> getObjects(){
		return myObjects;
	}
	public void step(){
		for(GameObject o: myObjects){
			if(o instanceof GravityForcable){
				applyGravity(o);
			}
			if(o instanceof InputUser){
				((InputUser) o).useInput();
			}
			o.fixSpeed();
			moveObject(o);
		}
	}
	
	public void moveObject(GameObject o){
		o.move();
	}
	public void applyGravity(GameObject o){
		((GravityForcable) o).applyGravity();
	}
	
	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public List<Camera> getCameras() {
		return myCameras;
	}
}
