package Model;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * 
 * @author Jacob
 * PhysicalObjects have hitboxes, which determine when and how objects collide.
 */
public class Hitbox {

	private List<Rectangle2D.Double> myHitbox;
	private double x;
	private double y;
	public Hitbox(List<Rectangle2D.Double> l){
		myHitbox = l;
		x = l.get(0).x;
		y = l.get(0).y;
	}
	/**
	 * Takes in another hitbox and returns true if the two are overlapping.
	 * @param hitbox
	 * @return true if the hitboxes overlap, false otherwise
	 */
	public boolean collides(Hitbox hitbox) {
		for(int i=0; i<myHitbox.size(); i++){
			for(int j=0; j<hitbox.myHitbox.size(); j++){
				if(myHitbox.get(i).intersects(hitbox.myHitbox.get(j))){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Moves hitbox along with player when called
	 * @param xdif difference in x to be added
	 * @param ydif difference in y to be added
	 */
	public void move(double newx, double newy) {
		double xdif = newx - x;
		double ydif = newy - y;
		x = newx;
		y = newy;
		for(Rectangle2D.Double r: myHitbox){
			r.x+=xdif;
			r.y+=ydif;
		}
	}
}
