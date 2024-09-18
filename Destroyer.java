/*
 * @author Cheng Erxi & Jiayi Wang
 */
public class Destroyer extends Ship {
	
	//static variables
	/**
	 * length of all destroyers
	 */
	private static int DESTROYER_LENGTH = 2;
	
	/**
	 * type of the ship 
	 */
	private static String DESTROYER_TYPE = "destroyer";
	
	//instance variables
	//inherit all instance variables from the superclass Ship
	
	/**
	 * 
	 *
	 */
	//constructor
	public Destroyer() {
		
		//call instructor in superclass Ship
		super(DESTROYER_LENGTH);
	}

	//methods

	/**
	 * return the type of destroyer
	 */
	@Override
	public String getShipType() {
		return DESTROYER_TYPE;
	}
}
