package Model;

/**
 * 
 * @author Jacob
 * An interface between the display and a given object on the 
 * screen for centering the screen around a given player.
 */
public class Camera {

	private GameObject myObject;
	public void addObject(GameObject g) {
		myObject = g;
	}
	public int getX(){
		return (int)myObject.getX();
	}
	public int getY(){
		return (int)myObject.getY();
	}
}
