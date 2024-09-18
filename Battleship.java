/*
* @author Cheng Erxi & Jiayi Wang
 */


public class Battleship extends Ship {

	//static variables
	/**
	 * length of all battleships
	 */
	private static int BATTLESHIP_LENGTH = 4;
	
	/**
	 * type of the ship 
	 */
	private static String BATTLESHIP_TYPE = "battleship";
	
	//instance variables
	//inherit all instance variables from the superclass Ship
	
	/**
	 * 
	 *
	 */
	//constructor
	public Battleship() {
		
		//call instructor in superclass Ship
		super(BATTLESHIP_LENGTH);
	}

	//methods

	/**
	 * return the type of battleship 
	 */
	@Override
	public String getShipType() {
		return BATTLESHIP_TYPE;
	}
	
	
}
