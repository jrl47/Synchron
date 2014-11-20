package Model;

/**
 * 
 * @author Jacob
 * Works with the Tile class to allow objects to only check local objects for collisions.
 */
public class TileGrid {

	private Tile[][] myGrid;
	public TileGrid(int tw, int th) {
		myGrid = new Tile[tw][th];
		for(int i=0; i<tw; i++){
			for(int j=0; j<th; j++){
				myGrid[i][j] = new Tile(i,j);
			}
		}
	}
	public Tile getTile(int x, int y){
		return myGrid[x][y];
	}
	public int getWidth(){
		return myGrid.length;
	}
	public int getHeight(){
		return myGrid[0].length;
	}
}
