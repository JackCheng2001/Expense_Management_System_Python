/*
 * @author Cheng Erxi & Jiayi Wang
 */
public class Submarine extends Ship {

	//static variables
	/**
	 * length of all submarines
	 */
	private static int SUBMARINE_LENGTH = 1;
	
	/**
	 * type of the ship 
	 */
	private static String SUBMARINE_TYPE = "submarine";
	
	//instance variables
	//inherit all instance variables from the superclass Ship
	
	/**
	 *
     */
	//constructor
	public Submarine() {
		
		//call instructor in superclass Ship
		super(SUBMARINE_LENGTH);
	}

	//methods

	/**
	 * return the type of submarine
	 */
	@Override
	public String getShipType() {
		return SUBMARINE_TYPE;
	}
}
