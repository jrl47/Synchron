package Forces;

public class PlayerInputData extends ForceData{

	public PlayerInputData(double leftCode, double rightCode, double jumpCode){
		myParameters.add(leftCode);
		myParameters.add(rightCode);
		myParameters.add(jumpCode);
	}
	
}
