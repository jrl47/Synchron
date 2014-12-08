package Forces;

import java.util.List;

import Model.GameObject;

public class Gravity extends Force{

	@Override
	public void doForce(List<Double> parameters, GameObject o) {
		o.incrementYVel(.95*parameters.get(0));
	}

}
