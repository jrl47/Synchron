package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jacob
 * Tiles exist to hold objects. This allows us to check for collisions with only local objects using the TileGrid.
 */
public class Tile {
	private int x;
	private int y;
	public List<PhysicalObject> myObjects;
	public Tile(int i, int j){
		x = i;
		y = j;
		myObjects = new ArrayList<PhysicalObject>();
	}
	public void addObject(PhysicalObject p){
		myObjects.add(p);
	}
	public void removeObject(PhysicalObject p){
		myObjects.remove(p);
	}
	public List<PhysicalObject> getObjects(){
		return myObjects;
	}
	public int getX(){
		return  x;
	}
	public int getY(){
		return  y;
	}
}
