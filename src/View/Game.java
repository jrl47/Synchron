package View;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import Controller.InputUser;
import Controller.MovementListener;
import Model.Stage;
import Model.StageBuilder;

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
	public static int scale = 2;
	
	private Thread thread;
	private Canvas canvas;
	private boolean exitGame;
	private JFrame frame;
	private MovementListener listener;
	private Display display;
	private StageBuilder sb;
	private Stage s;
	public Game(){
		Dimension size = new Dimension(width*scale, height*scale);
		listener = new MovementListener();
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
    	sb = new StageBuilder(listener);
    	s = sb.buildInitialStage();
		init();
	}
	public synchronized void init(){
		thread = new Thread(this, "Synchron");
		exitGame = false;
		frame.setFocusable(true);
		frame.addKeyListener(new MovementListener());
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
		s.step();
	}
	public void render() {
		display.render(s.getObjects(), s.getCamera());
	}
	public void useInput() {
		
	}
}
