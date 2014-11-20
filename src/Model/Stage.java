package Model;

import java.util.ArrayList;
import java.util.List;

import Controller.DragForcable;
import Controller.GravityForcable;
import Controller.InputUser;

/**
 * 
 * @author Jacob
 * An area for objects to exist and interact on.
 */
public class Stage {
	private int width;
	private int height;
	TileGrid myGrid;
	private List<GameObject> myObjects;
	public Stage(int w, int h, int tw, int th){
		width = w;
		height = h;
		myGrid = new TileGrid(tw, th);
		myObjects = new ArrayList<GameObject>();
	}
	public void addObject(GameObject o){
		myObjects.add(o);
	}
	public List<GameObject> getObjects(){
		return myObjects;
	}
	public void step(){
		for(GameObject o: myObjects){
			if(o instanceof GravityForcable){
				((GravityForcable) o).applyGravity();
			}
			if(o instanceof DragForcable){
				((DragForcable) o).applyDrag();
			}
			if(o instanceof InputUser){
				((InputUser) o).useInput();
			}
			o.fixSpeed();
			o.move();
		}
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
