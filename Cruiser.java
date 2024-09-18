/*
 * @author Cheng Erxi & Jiayi Wang
 */
public class Cruiser extends Ship {
	//static variables
	/**
	 * length of all cruisers
	 */
	private static int CRUISER_LENGTH = 3;
	
	/**
	 * type of the ship 
	 */
	private static String CRUISER_TYPE = "cruiser";
	
	//instance variables
	//inherit all instance variables from the superclass Ship
	
	/**
	 * 
	 *
	 */
	//constructor
	public Cruiser() {
		
		//call instructor in superclass Ship
		super(CRUISER_LENGTH);
	}

	//methods

	/**
	 * return the type of cruiser
	 */
	@Override
	public String getShipType() {
		return CRUISER_TYPE;
	}
}
