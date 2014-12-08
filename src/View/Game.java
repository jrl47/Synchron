package View;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import Controller.InputUser;
import Controller.MovementListener;
import Model.Stage;
import Model.StageManager;

/**
 * 
 * @author Jacob
 * Keeps track of universal values and sets up Game framework.
 */
public class Game implements Runnable, InputUser{
	private final double TICK_LENGTH = 1000000000.0/30.0;
	public final static double MAX_SPEED = 12;
	
	public static int width = 800;
	public static int height = (width / 16) * 9;
	public static double scale = 2;
	
	private Thread thread;
	private Canvas canvas;
	private boolean exitGame;
	private JFrame frame;
	private Display display;
	private StageManager manager;
	public Game(){
		Dimension size = new Dimension((int)(width*scale), (int)(height*scale));
		MovementListener listener = new MovementListener();
		canvas = new Canvas();
		canvas.setPreferredSize(size);
		canvas.addKeyListener(listener);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Synchron");
		frame.add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);
    	display = new Display(width, height, canvas);
    	manager = new StageManager();
    	manager.buildInitialStage(listener);
		init();
	}
	public synchronized void init(){
		thread = new Thread(this, "Synchron");
		exitGame = false;
		frame.setFocusable(true);
		thread.start();
	}
	public void run() {
		long firstTime = System.nanoTime();
		long ticks = 0;
		while(exitGame==false){
			step();
			render();
			long now = System.nanoTime();
			while(now - (firstTime+ticks*TICK_LENGTH) < TICK_LENGTH){
				Thread.yield();
				try{
					Thread.sleep(1);
				} catch(Exception e) {}
				now = System.nanoTime();
			}
			ticks++;
		}
	}
	public synchronized void exit(){
		exitGame = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void step() {
		manager.getStage().step();
	}
	public void render() {
		display.render(manager.getStage().getObjects(), manager.getStage().getCameras());
	}
	public void useInput() {
		
	}
}
