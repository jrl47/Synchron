package Model;

import java.awt.Rectangle;
import java.util.List;

import Controller.InputUser;
import Controller.MovementListener;
import Forces.ForceData;
import View.Sprite;


/**
 * 
 * @author Jacob
 * A PhysicalObject which is controlled by the user to move about and interact.
 */
public class Player extends PhysicalObject implements InputUser{

	private int leftCode;
	private int rightCode;
	private int jumpCode;
	private MovementListener listener;
	public Player(double xx, double yy, Sprite s, Stage st, int left, int right, int jump, MovementListener lis, List<ForceData> forces) {
		super(xx, yy, s, st, forces);
		leftCode = left;
		rightCode = right;
		jumpCode = jump;
		listener = lis;
	}
	
	public void useInput() {
		if(listener.getKey().contains(leftCode))
			xvel = -5;
		if(listener.getKey().contains(rightCode))
			xvel = 5;
		if(!(listener.getKey().contains(leftCode))&&!(listener.getKey().contains(rightCode))){
			xvel = 0;
		}
		if(listener.getKey().contains(jumpCode)  && isOnSurface())
			yvel -= 16;
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
}
