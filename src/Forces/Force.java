package Forces;

import java.util.List;

import Model.GameObject;
import Model.Stage;

public abstract class Force {
	
	// Calls this to apply a force to an object
	public void applyForce(GameObject o){
		if(getForce(o)!=null)
			doForce(getForce(o), o);
	}
	
	// Retrieves relevant force parameters from object
	public List<Double> getForce(GameObject o){
		String relevantData = this.getClass().getSimpleName() + "Data";
		List<ForceData> forces = o.getForces();
		List<Double> parameters = null;
		if(forces==null)
			return null;
		for(int i=0; i<forces.size(); i++){
			if(forces.get(i).getClass().getSimpleName().equals(relevantData))
				parameters = forces.get(i).getParameters();
		}
		return parameters;
	}
	
	// This method actually carries out the force in question
	public abstract void doForce(List<Double> parameters, GameObject o);
	
}
