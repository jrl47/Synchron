package Forces;

import java.util.List;

import Controller.MovementListener;
import Model.GameObject;
import Model.PhysicalObject;

public class PlayerInput extends Force{

	MovementListener listener;
	public PlayerInput(MovementListener lis){
		listener = lis;
	}
	
	@Override
	public void doForce(List<Double> parameters, GameObject o) {
		int leftCode = parameters.get(0).intValue();
		int rightCode = parameters.get(1).intValue();
		int jumpCode = parameters.get(2).intValue();
		
		if(listener.getKey().contains(leftCode)){
			o.setXVel(-5);
		}
		if(listener.getKey().contains(rightCode))
			o.setXVel(5);
		if(!(listener.getKey().contains(leftCode))&&!(listener.getKey().contains(rightCode))){
			o.setXVel(0);
		}
		if(listener.getKey().contains(jumpCode)  && ((PhysicalObject) o).isOnSurface())
			o.setYVel(-16);
	}

}
