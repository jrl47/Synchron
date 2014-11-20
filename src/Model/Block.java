package Model;

import View.Sprite;

/**
 * 
 * @author Jacob
 * A simple stationary collideable rectangle.
 */
public class Block extends PhysicalObject {

	public Block(double xx, double yy, Sprite s, Stage st) {
		super(xx, yy, s, st);
	}
}
