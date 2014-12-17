package Model;

import java.util.List;

import Forces.ForceData;
import View.Game;
import View.Sprite;

/**
 * 
 * @author Jacob
 * A game object. Has a position, velocity, sprite, existence property, and associated stage.
 */
public class GameObject {
	protected double x;
	protected double y;
	protected double z;
	protected double xvel;
	protected double yvel;
	private Sprite mySprite;
	protected List<ForceData> myForces;
	public GameObject(double xx, double yy, Sprite s, double zz, List<ForceData> forces){
		x = xx;
		y = yy;
		z = zz;
		mySprite = s;
		myForces = forces;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getXVel(){
		return xvel;
	}
	public double getYVel(){
		return yvel;
	}
	public Sprite getSprite(){
		return mySprite;
	}
	public void incrementX(double xinc){
		x+= xinc;
	}
	public void incrementY(double yinc){
		y+= yinc;
	}
	public void incrementXVel(double xinc){
		xvel+= xinc;
	}
	public void incrementYVel(double yinc){
		yvel+= yinc;
	}
	public void setXVel(double xset){
		xvel = xset;
	}
	public void setYVel(double yset){
		yvel = yset;
	}
	public double getZ(){
		return z;
	}
	/**
	 * No object can exceed the max speed of the game.
	 */
	public void fixSpeed() {
		if(xvel > Game.MAX_SPEED)
			xvel = Game.MAX_SPEED;
		if(xvel < -1*Game.MAX_SPEED)
			xvel = -1*Game.MAX_SPEED;
		if(yvel > Game.MAX_SPEED)
			yvel = Game.MAX_SPEED;
		if(yvel < -1*Game.MAX_SPEED)
			yvel = -1*Game.MAX_SPEED;
	}
	public List<ForceData> getForces() {
		return myForces;
	}

}
