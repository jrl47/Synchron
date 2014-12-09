package Model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Forces.ForceData;
import Forces.PlayerInputData;
import View.Game;
import View.Sprite;

/**
 * 
 * @author Jacob
 * 
 * A GameObject that has a HitBox and thus can interact with other objects via collision.
 */
public class PhysicalObject extends GameObject{

	protected Hitbox hitbox;
	private Tile myTile;
	public PhysicalObject(double xx, double yy, Sprite s, Stage st, List<ForceData> forces) {
		super(xx, yy, s, st, 1, forces);
		List<Rectangle2D.Double> rects = new ArrayList<Rectangle2D.Double>();
		rects.add(new Rectangle2D.Double((int)xx, (int)yy, s.XSIZE, s.YSIZE));
		myTile = myStage.myGrid.getTile(xToGridX(xx),yToGridY(yy));
		myTile.addObject(this);
		hitbox = new Hitbox(rects);
	}
	/**
	 * 
	 * @return
	 * Converts an x-location in pixels to an x-location in tiles
	 */
	public int xToGridX(double x){
		return (int)(((double)x)*((double)myStage.myGrid.getWidth())/((double)myStage.getWidth()));
	}
	/**
	 * 
	 * @return
	 * Converts an y-location in pixels to an y-location in tiles
	 */
	public int yToGridY(double y){
		return (int)(((double)y)*((double)myStage.myGrid.getHeight())/((double)myStage.getHeight()));
	}
	
	public boolean isOnSurface() {
		double oldyvel = yvel;
		yvel = 1;
		boolean result = false;
		if(checkAdjacencyY())
			result = true;
		yvel = oldyvel;
		return result;
	}
	@Override
	public void move(){
		boolean isInputUser = false;
		if(myForces!=null){
		for(ForceData f: myForces){
			if(f instanceof PlayerInputData)
				isInputUser = true;
		}
		}
		if(isInputUser){
		myTile.removeObject(this);
		applyAdjustments();
		hitbox.move(x, y);
		myTile = myStage.myGrid.getTile(xToGridX(x),yToGridY(y));
		myTile.addObject(this);
		}
		else{
			super.move();
		}
	}
	/**
	 * moves the object to where it needs to be considering nearby obstacles
	 */
	public void applyAdjustments(){
		boolean done = false;
		boolean xdone = false;
		boolean ydone = false;
		int xinc = 0;
		int yinc = 0;
		int xsign = (int) Math.signum(xvel);
		int ysign = (int) Math.signum(yvel);
		while(!done){
			if(checkAdjacencyX() && !checkAdjacencyY()){
				xvel = 0;
				xdone = true;
			}
			if(checkAdjacencyY() && !checkAdjacencyX()){
				yvel = 0;
				ydone = true;
			}
			if(checkAdjacencyX() && checkAdjacencyY()){
				yvel = 0;
				xvel = 0;
				xdone = true;
				ydone = true;
			}
			if(checkAdjacencyBoth() && !checkAdjacencyX() && !checkAdjacencyY()){
				xvel = 0;
				xdone = true;
			}
			if(Math.abs(xinc) < Math.abs(xvel) && !xdone){
				x+=xsign;
				xinc++;
			}
			else{
				xdone = true;
			}
			if(Math.abs(yinc) < Math.abs(yvel) && !ydone){
				y+=ysign;
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
	/**
	 * 
	 * @return whether the object is directly (1 pixel away) next to an object in the direction of its xy-velocity
	 */
	protected boolean checkAdjacencyBoth(){
		boolean result = false;
		if(xvel>=0 && yvel >=0){
			x++;
			y++;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			x--;
			y--;
		}
		if(xvel>=0 && yvel <0){
			x++;
			y--;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			x--;
			y++;
		}
		if(xvel<0 && yvel >=0){
			x--;
			y++;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			x++;
			y--;
		}
		if(xvel<0 && yvel <0){
			x--;
			y--;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			x++;
			y++;
		}
		return result;
	}
	/**
	 * 
	 * @return whether the object is directly (1 pixel away) next to an object in the direction of its x-velocity
	 */
	protected boolean checkAdjacencyX(){
		boolean result = false;
		if(xvel>=0){
			x++;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			x--;
		}
		if(xvel<0){
			x--;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			x++;
		}
		return result;
	}
	/**
	 * 
	 * @return whether the object is directly (1 pixel away) next to an object in the direction of its y-velocity
	 */
	protected boolean checkAdjacencyY(){
		boolean result = false;
		if(yvel>=0){
			y++;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			y--;
		}
		if(yvel<0){
			y--;
			hitbox.move(x,y);
			for(PhysicalObject p: getCloseObjects()){
				if(p.collides(this))
					result = true;
			}
			y++;
		}
		return result;
	}
	/**
	 * 
	 * @return list of objects nearby based on underlying tile grid
	 */
	public List<PhysicalObject> getCloseObjects(){
		List<PhysicalObject> closeObjects = new ArrayList<PhysicalObject>();
		Tile t;
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
				if(myTile.getX() + i >= 0 && myTile.getX() + i < myStage.myGrid.getWidth()
						&& myTile.getY() + j >= 0 && myTile.getY() + j < myStage.myGrid.getHeight()){
					t = myStage.myGrid.getTile(myTile.getX() + i, myTile.getY() + j);
					closeObjects.addAll(t.getObjects());
				}
			}
		}
		closeObjects.remove(this);
		return closeObjects;
	}
	protected boolean collides(PhysicalObject p){
		return hitbox.collides(p.hitbox);
	}
}
