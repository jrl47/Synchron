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
	private boolean onSurface;
	public PhysicalObject(double xx, double yy, Sprite s, List<ForceData> forces) {
		super(xx, yy, s, 1, forces);
		List<Rectangle2D.Double> rects = new ArrayList<Rectangle2D.Double>();
		rects.add(new Rectangle2D.Double((int)xx, (int)yy, s.XSIZE, s.YSIZE));
		hitbox = new Hitbox(rects);
		onSurface = false;
	}
	
	
	public boolean isOnSurface() {
		return onSurface;
//		double oldyvel = yvel;
//		yvel = 1;
//		boolean result = false;
//		if(checkAdjacencyY())
//			result = true;
//		yvel = oldyvel;
//		return result;
	}
	public void setOnSurface(boolean s){
		onSurface = s;
	}

	
	/**
	 * Moves the hitbox to the location of the object during a move
	 */
	public void adjustHitbox(){
		hitbox.move(x, y);
	}
	/**
	 * 
	 * @param os 
	 * @return whether the object is directly (1 pixel away) next to an object in the direction of its xy-velocity
	 */
	protected boolean checkAdjacencyBoth(List<PhysicalObject> os){
		boolean result = false;
		if(xvel>=0 && yvel >=0){
			x++;
			y++;
			hitbox.move(x,y);
			for(PhysicalObject p: os){
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
			for(PhysicalObject p: os){
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
			for(PhysicalObject p: os){
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
			for(PhysicalObject p: os){
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
	 * @param os 
	 * @return whether the object is directly (1 pixel away) next to an object in the direction of its x-velocity
	 */
	protected boolean checkAdjacencyX(List<PhysicalObject> os){
		boolean result = false;
		if(xvel>=0){
			x++;
			hitbox.move(x,y);
			for(PhysicalObject p: os){
				if(p.collides(this))
					result = true;
			}
			x--;
		}
		if(xvel<0){
			x--;
			hitbox.move(x,y);
			for(PhysicalObject p: os){
				if(p.collides(this))
					result = true;
			}
			x++;
		}
		return result;
	}
	/**
	 * 
	 * @param os 
	 * @return whether the object is directly (1 pixel away) next to an object in the direction of its y-velocity
	 */
	protected boolean checkAdjacencyY(List<PhysicalObject> os){
		boolean result = false;
		if(yvel>=0){
			y++;
			hitbox.move(x,y);
			for(PhysicalObject p: os){
				if(p.collides(this))
					result = true;
			}
			y--;
		}
		if(yvel<0){
			y--;
			hitbox.move(x,y);
			for(PhysicalObject p: os){
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
	protected boolean collides(PhysicalObject p){
		return hitbox.collides(p.hitbox);
	}
}
