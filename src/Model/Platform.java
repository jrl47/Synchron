package Model;

import View.Sprite;

public class Platform extends PhysicalObject{

	public Platform(double xx, double yy, Sprite s, Stage st) {
		super(xx, yy, s, st);
	}
	@Override
	protected boolean collides(PhysicalObject p){
		if(p.yvel > 0)
			return hitbox.collides(p.hitbox);
		return false;
	}
}