package Model;

import java.awt.Rectangle;

import Controller.DragForcable;
import Controller.GravityForcable;
import Controller.InputUser;
import Controller.MovementListener;
import View.Sprite;


/**
 * 
 * @author Jacob
 * A PhysicalObject which is controlled by the user to move about and interact.
 */
public class Player extends PhysicalObject implements InputUser, GravityForcable, DragForcable{

	private int leftCode;
	private int rightCode;
	private int jumpCode;
	private MovementListener listener;
	public Player(double xx, double yy, Sprite s, Stage st, int left, int right, int jump, MovementListener lis) {
		super(xx, yy, s, st);
		leftCode = left;
		rightCode = right;
		jumpCode = jump;
		listener = lis;
	}
	
	public void useInput() {
		if(listener.getKey().contains(leftCode))
			xvel = -4;
		if(listener.getKey().contains(rightCode))
			xvel = 4;
		if(listener.getKey().contains(jumpCode)  && isOnSurface())
			yvel -= 12;
	}

	private boolean isOnSurface() {
		double oldyvel = yvel;
		yvel = 1;
		boolean result = false;
		if(checkAdjacencyY())
			result = true;
		yvel = oldyvel;
		return result;
	}

	public void applyDrag() {
		xvel = 0;
	}

	public void applyGravity() {
		yvel += .99;
	}
}
