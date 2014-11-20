package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Jacob
 * 
 * Keeps track of all currently pressed keys.
 */
public class MovementListener implements KeyListener{
	
	private Set<Integer> myKeys = new HashSet<Integer>();
	public void keyPressed(KeyEvent e) {
		myKeys.add(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		myKeys.remove(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {

	}
	public Set<Integer> getKey(){
		return myKeys;
	}
}
