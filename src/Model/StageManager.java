package Model;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Controller.MovementListener;
import View.Game;
import View.PixelArray;
import View.Sprite;
import View.SpriteSheet;

/**
 * 
 * @author Jacob
 * A class for constructing stages.
 */
public class StageManager {
	
	Stage myStage;
	public StageManager() {
		
	}

	public Stage buildInitialStage(MovementListener listener){
		if(myStage==null){
			List<String> data = null;
			try {
				data = Files.readAllLines(Paths.get("resources/leveldata.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myStage = new Stage(listener, new StageData(data));
		}
		return myStage;
	}
	
	public Stage getStage(){
		return myStage;
	}
	
}
