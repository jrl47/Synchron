package Model;

public class Camera {

	private GameObject myObject;
	public void addObject(Player p) {
		myObject = p;
	}
	public int getX(){
		return (int)myObject.getX();
	}
	public int getY(){
		return (int)myObject.getY();
	}
}
