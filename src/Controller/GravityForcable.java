package Controller;

// Gravity, a force influencing velocity at all times, is applied to this object.
public interface GravityForcable {
	
	// Allows objects to be more or less affected by gravity as desired when the Stage moves them.
	public void applyGravity();
	
}