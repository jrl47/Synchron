package Model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import View.Game;
import View.Sprite;

/**
 * 
 * @author Jacob
 * 
 * A GameObject that has a HitBox and thus can interact with other objects via collision.
 */
public class PhysicalObject extends GameObject{

	private Hitbox hitbox;
	private Tile myTile;
	public PhysicalObject(double xx, double yy, Sprite s, Stage st) {
		super(xx, yy, s, st);
		List<Rectangle2D.Double> rects = new ArrayList<Rectangle2D.Double>();
		rects.add(new Rectangle2D.Double((int)xx, (int)yy, s.XSIZE, s.YSIZE));
		myTile = myStage.myGrid.getTile(xToGridX(xx),yToGridY(yy));
		myTile.addObject(this);
		hitbox = new Hitbox(rects);
	}
	@Override
	public void move(){
		myTile.removeObject(this);
		move(getCollisionXScalar(),getCollisionYScalar());
		hitbox.move(x, y);
		myTile = myStage.myGrid.getTile(xToGridX(x),yToGridY(y));
		myTile.addObject(this);
	}
	/**
	 * Moves an object with a given scale factor to avoid collision.
	 * @param scale
	 */
	private void move(double scalex, double scaley) {
		if(scalex!=Integer.MAX_VALUE){
		xvel = xvel/scalex;
		}
		else{
			xvel=0;
		}
		if(scaley!=Integer.MAX_VALUE){
		yvel = yvel/scaley;
		}
		else{
			yvel=0;
		}
		x+=xvel;
		y+=yvel;
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
	/**
	 * Gets collision scalar by figuring out the highest same-direction velocity that would result in given objects not colliding.
	 * @return Returns the scale factor needed to reset velocity to prevent collision.
	 */
	
	public double getCollisionXScalar(){
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
		double collided = 1;
		for(PhysicalObject p : closeObjects){
			collided = Math.max(collided,collideXScalar(p));
		}
		return collided;
	}

	/**
	 * Gets scalar for particular collisions
	 * @param p object we are checking for collision
	 * @return Scale factor for velocity for collision
	 */
	public double collideXScalar(PhysicalObject p){
		x += xvel;
		hitbox.move(x, y);
		double xorig = xvel;
		int scale = 1;
		while(hitbox.collides(p.hitbox)){
			x -= xvel;
			hitbox.move(x, y);
			scale++;
			xvel = xorig/scale;
			x += xvel;
			hitbox.move(x, y);
			if(scale > Game.MAX_SPEED){
				scale = Integer.MAX_VALUE;
				break;
			}
		}
		x-= xvel;
		hitbox.move(x, y);
		xvel = xorig;
		return scale;
	}
	public double getCollisionYScalar(){
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
		double collided = 1;
		for(PhysicalObject p : closeObjects){
			collided = Math.max(collided,collideYScalar(p));
		}
		return collided;
	}

	/**
	 * Gets scalar for particular collisions
	 * @param p object we are checking for collision
	 * @return Scale factor for velocity for collision
	 */
	public double collideYScalar(PhysicalObject p){
		y += yvel;
		hitbox.move(x, y);
		double yorig = yvel;
		double scale = 1;
		while(hitbox.collides(p.hitbox)){
			y -= yvel;
			hitbox.move(x, y);
			scale++;
			yvel = yorig/scale;
			y += yvel;
			hitbox.move(x, y);
			if(scale > Game.MAX_SPEED){
				scale = Integer.MAX_VALUE;
				break;
			}
		}
		y-= yvel;
		hitbox.move(x, y);
		yvel = yorig;
		return scale;
	}
}
