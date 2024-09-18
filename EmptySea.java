/*
 * @author Cheng Erxi & Jiayi Wang
 */
public class EmptySea extends Ship {

	//static variables
	/**
	 * length of all empty sea
	 */
	private static int EMPTYSEA_LENGTH = 1;
	
	/**
	 * type of the ship 
	 */
	private static String EMPTYSEA_TYPE = "empty";
	
	//instance variables
	//inherit all instance variables from the superclass Ship
	
	/**
	 *
     */
	//constructor
	public EmptySea() {
		
		//call instructor in superclass Ship
		super(EMPTYSEA_LENGTH);
	}

	//methods

	/**
	 * overrides shootAt inherited from Ship
	 * always return false to indicate that nothing has hit
	 */
	@Override
	public boolean shootAt(int row, int column) {
		
		//the local hit is changed to true
		this.getHit()[0] = true;
		
		//always return false
		return false;
	}
	
	/**
	 * overrides isSunk inherited from Ship
	 * always returns false 
	 */
	@Override
	public boolean isSunk() {
		//always return false
		return false;
	}
	

	/**
	 * return single character "-" 
	 */
	@Override
	public String toString() {
		
		if (this.getHit()[0] == true) {
			//return "-" when a shoot has been fired but nothing has been hit

			return "-";
		}

		return ".";		
	}
	
	
	/**
	 * return the type of battleship 
	 */
	@Override
	public String getShipType() {
		return EMPTYSEA_TYPE;
	}
	
}
