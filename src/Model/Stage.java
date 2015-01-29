package Model;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Controller.InputUser;
import Controller.MovementListener;
import Forces.Force;
import Forces.ForceData;
import Forces.Gravity;
import Forces.GravityData;
import Forces.PlayerInput;
import Forces.PlayerInputData;
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
	private List<Force> myForces;
	private MovementListener listener;
	
	public Stage(MovementListener lis, StageData stageData){
		listener = lis;
		
		BufferedImage image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		try {
			image = ImageIO.read(Stage.class.getResource("/level.png"));
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
		
		myForces = new ArrayList<Force>();
		myForces.add(new Gravity());
		myForces.add(new PlayerInput(listener));
		
		List<ForceData> playerOneForces = new ArrayList<ForceData>();
		List<ForceData> playerTwoForces = new ArrayList<ForceData>();
		playerOneForces.add(new GravityData(1));
		playerTwoForces.add(new GravityData(1));
		playerOneForces.add(new PlayerInputData(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W));
		playerTwoForces.add(new PlayerInputData(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP));
		
		SpriteSheet bg = new SpriteSheet("/amazingbackground.png", map.getWidth()*16, map.getHeight()*16);
		SpriteSheet sheet = new SpriteSheet("/lightdeciduousspritesheet.png", 80, 176);
		Sprite back = new Sprite(map.getWidth()*16, map.getHeight()*16, 0, 0, bg);
		Sprite dirt = new Sprite(16, 16, 1, 1, sheet);
		Sprite redGuy = new Sprite(16, 16, 0, 10, sheet);
		Camera c1 = new Camera();
		Camera c2 = new Camera();
		List<Camera> cameras = new ArrayList<Camera>();
		cameras.add(c1); cameras.add(c2);
		PhysicalObject player1 = new PhysicalObject(98*16, 170*16, redGuy, playerOneForces);
		c1.addObject(player1);
		PhysicalObject player2 = new PhysicalObject(99*16, 170*16, redGuy, playerTwoForces);
		c2.addObject(player2);
		myCameras = cameras;
		addObject(player1);
		addObject(player2);
		addObject(new GameObject(0, 0, back, 0, null));
		
		for(int i=0; i<map.getWidth(); i++){
			for(int j=0; j<map.getHeight(); j++){
				if(map.getPixel(i, j)==-16777216){
					addObject(new PhysicalObject(16*i, 16*j, dirt, null));
				}
			}
		}
	}
	public void addObject(GameObject o){
		if(o instanceof PhysicalObject){
			myGrid.getTile(xToGridX(o.getX()),yToGridY(o.getY())).addObject((PhysicalObject)o);
		}
		myObjects.add(o);
	}
	public List<GameObject> getObjects(){
		return myObjects;
	}
	public void step(){
		for(GameObject o: myObjects){
			applyForces(o);
			o.fixSpeed();
			moveObject(o);
		}
	}
	
	public void moveObject(GameObject o){
		if(o instanceof PhysicalObject){
			
			boolean isInputUser = false;
			if(o.getForces()!=null){
			for(ForceData f: o.getForces()){
				if(f instanceof PlayerInputData)
					isInputUser = true;
			}
			}
			if(isInputUser){
				Tile myTile = myGrid.getTile(xToGridX(o.getX()),yToGridY(o.getY()));
				myTile.removeObject((PhysicalObject)o);
				applyAdjustments((PhysicalObject)o);
				((PhysicalObject) o).adjustHitbox();
				myTile = myGrid.getTile(xToGridX(o.getX()),yToGridY(o.getY()));
				myTile.addObject((PhysicalObject)o);
			}
			else{
				o.incrementX(o.getXVel());
				o.incrementY(o.getYVel());
			}
		}
		else{
			o.incrementX(o.getXVel());
			o.incrementY(o.getYVel());
		}
	}
	/**
	 * moves the object to where it needs to be considering nearby obstacles
	 */
	private void applyAdjustments(PhysicalObject o) {
		
		o.setOnSurface(false);
		boolean done = false;
		boolean xdone = false;
		boolean ydone = false;
		List<PhysicalObject> os = getCloseObjects(myGrid.getTile(xToGridX(o.getX()), yToGridY(o.getY())));
		int xinc = 0;
		int yinc = 0;
		int xsign = (int) Math.signum(o.getXVel());
		int ysign = (int) Math.signum(o.getYVel());
		while(!done){
			if(o.checkAdjacencyX(os) && !o.checkAdjacencyY(os)){
				o.setXVel(0);
				xdone = true;
			}
			if(o.checkAdjacencyY(os) && !o.checkAdjacencyX(os)){
				if(o.getYVel()>0)
				o.setOnSurface(true);
				o.setYVel(0);
				ydone = true;
			}
			if(o.checkAdjacencyX(os) && o.checkAdjacencyY(os)){
				if(o.getYVel()>0)
				o.setOnSurface(true);
				o.setXVel(0);
				o.setYVel(0);
				xdone = true;
				ydone = true;
			}
			if(o.checkAdjacencyBoth(os) && !o.checkAdjacencyX(os) && !o.checkAdjacencyY(os)){
				o.setXVel(0);
				xdone = true;
			}
			if(Math.abs(xinc) < Math.abs(o.getXVel()) && !xdone){
				o.incrementX(xsign);
				xinc++;
			}
			else{
				xdone = true;
			}
			if(Math.abs(yinc) < Math.abs(o.getYVel()) && !ydone){
				o.incrementY(ysign);
				yinc++;
			}
			else{
				ydone = true;
			}
			if(xdone && ydone){
				done=true;
			}
		}
	}
	public List<PhysicalObject> getCloseObjects(Tile myTile){
		List<PhysicalObject> closeObjects = new ArrayList<PhysicalObject>();
		Tile t;
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
				if(myTile.getX() + i >= 0 && myTile.getX() + i < myGrid.getWidth()
						&& myTile.getY() + j >= 0 && myTile.getY() + j < myGrid.getHeight()){
					t = myGrid.getTile(myTile.getX() + i, myTile.getY() + j);
					closeObjects.addAll(t.getObjects());
				}
			}
		}
		closeObjects.remove(this);
		return closeObjects;
	}
	public void applyForces(GameObject o){
		for(Force f: myForces){
			f.applyForce(o);
		}
	}
	/**
	 * 
	 * @return
	 * Converts an x-location in pixels to an x-location in tiles
	 */
	public int xToGridX(double x){
		return (int)(((double)x)*((double)myGrid.getWidth())/((double)width));
	}
	/**
	 * 
	 * @return
	 * Converts an y-location in pixels to an y-location in tiles
	 */
	public int yToGridY(double y){
		return (int)(((double)y)*((double)myGrid.getHeight())/((double)height));
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
