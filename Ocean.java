/*
 * @author Cheng Erxi & Jiayi Wang
 */
import java.util.Objects;
import java.util.Random;

public class Ocean {
	
	//instance variables
	/**
	 * determine which ship is in any given location
	 */
	private Ship[][] ships = new Ship[10][10];
	
	/**
	 * total number of shots fired by the user
	 */
	private int shotsFired;
	
	/**
	 * number of times a shot hit a ship
	 */
	private int hitCount;
		
	/**
	 * number of ships sunk
	 */
	private int shipsSunk;
	
	//constructor
	private int initiator=0;
	
	/**
	 * create an empty ocean and fills the ships array with empty sea objects 
	 */
	public Ocean() {
		//initialize the shots fired and hit count to be 0
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		
		//initialize the 2D array and fill each element with a new Empty Sea 
		for (int i = 0; i < 10; i++ ) {
			for (int j = 0; j < 10; j++) {
				
				//set each element to empty sea
				ships[i][j] = new EmptySea();
				ships[i][j].placeShipAt(i, j, true,this);
			}
		}			
	}
	
	//methods
	/**
	 * place all ten ships randomly on the initial ocean
	 * place larger ships before smaller ones
	 */
	public void placeAllShipsRandomly() {
		//initialize an array which contains all the ten ships
		Ship[] playerShips = {new Battleship(), new Cruiser(), new Cruiser(),
									new Destroyer(), new Destroyer(), new Destroyer(),
									new Submarine(), new Submarine(), new Submarine(), new Submarine()};
	
		
		//initialize the random variable 
		Random random_choice = new Random();
		
		//iterate over the playerShips list to place each ship on the ocean map
		for (int i = 0; i < 10; i++) {
			
			//store the ships in the playerShips list to a ship variable
			Ship current_ship = playerShips[i];
			
			boolean condition = true;
			
			while(condition) {

				//randomly assign the ship to be horizontal or vertical
				boolean horizontal = random_choice.nextInt(2) == 0;
				//randomly choose the row and column to place the bow of each ship

				int ship_row = random_choice.nextInt(10);
				int ship_col = random_choice.nextInt(10);



				//check if the ship can be placed here
				if (current_ship.okToPlaceShipAt(ship_row, ship_col,horizontal,this)) {
					
					//if the ship can be placed here, placed the ship at this location
					current_ship.placeShipAt(ship_row, ship_col, horizontal, this);
					break;
					
				}			
			}			
		}
	}
	
	/**
	 * return true if the given location contains a ship, false it is not 
	 * @param row to check
	 * @param column to check 
	 * @return true or false
	 */
	boolean isOccupied(int row, int column) {
		
		//if the (row,column) is out of range, return false
		if (row < 0 || row > 9 || column < 0 || column > 9) {
			return false;
		}
		
		//if the (row,column) is an instance of empty sea, return false
		if (this.ships[row][column] instanceof Battleship || this.ships[row][column] instanceof Cruiser||this.ships[row][column] instanceof Destroyer||this.ships[row][column] instanceof Submarine) {
			return true;
		}

		return false;
	}
	
	/**
	 * return true if given location contains a real ship, false if it does not
	 * @param row to shoot
	 * @param column to shoot
	 * @return true if shoots at the same place; if ship has been sunk, additional shots are false
	 */
	public boolean shootAt(int row, int column) {
		//increment the number of shots fired
		shotsFired++;
		
		
		//check if there is a ship at that location
		if (ships[row][column].isSunk() == false) {
			


			
			//if the ship at the location is not sunk
			if (ships[row][column].shootAt(row, column)) {
				//increment the count of hits
				hitCount++;
				return true;
			}

			return false;
		} else {
			return false;
					
		}		
	}
	
	/**
	 * @return number of shots fired
	 */
	public int getShotsFired() {
		return this.shotsFired;
	}
	
	/**
	 * @return number of hits recorded 
	 */
	public int getHitCount() {
		return this.hitCount;
	}
	
	/**
	 * @return number of ships sunk
	 */
	public int getShipsSunk() {
		this.shipsSunk=0;
		double counter=0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				//check if all location in the map has a map that is sunk
				if(ships[i][j].isSunk()) {
					counter += (double) 1 /(ships[i][j].getLength());
				}
			}
		}
		this.shipsSunk=(int) Math.round(counter);
		return this.shipsSunk;
	}
	
	/**
	 * @return true if all ships have been sunk, otherwise false
	 */
	public boolean isGameOver() {
		//initialize a variable for recording the number of ships 
		int sunkShipsCount = 0;
		
		//iterate through the 2D array of ships in ocean
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				
				//check if all location in the map has a map that is sunk
				if(ships[i][j].isSunk()) {
					sunkShipsCount++;
				}
			}
		}
		
		if (sunkShipsCount == 20) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return 10x10 array of ships
	 */
	public Ship[][] getShipArray(){
		return ships;
	}
		
	/**
	 * Prints the Ocean
	 * row numbers should be displayed along the left edge of the array
	 * column numbers should be displayed along the top
	 */
	public void print() {
		// Print the column numbers
		System.out.print("  ");
		for (int i = 0; i < ships[0].length; i++) {
			System.out.print(i + " ");
		}
		System.out.println();

		for (int i = 0; i < ships.length; i++) {
			// Print the row number
			System.out.print(i + " ");

			for (int j = 0; j < ships[i].length; j++) {
				if (ships[i][j].isHitAt(i, j)) {
					// Only call toString on this ship if it's been hit at this location
					System.out.print(ships[i][j].toString());
				} else {
					// Otherwise, print a dot to signify that this location hasn't been hit
					System.out.print(".");
				}
				System.out.print(" ");
			}
			System.out.println(); // Move to the next line after printing the row
		}
	}


	public void printWithShips() {
		// Print the column numbers
		System.out.print("  ");
		for (int i = 0; i < ships[0].length; i++) {
			System.out.print(i + " ");
		}
		System.out.println();

		for (int row = 0; row < ships.length; row++) {
			// Print the row number
			System.out.print(row + " ");

			for (int col = 0; col < ships[row].length; col++) {
				if (Objects.equals(ships[row][col].getShipType(), "empty")) {
					System.out.print("  ");  // Two spaces for EmptySea to align with single characters and spaces
				} else {
					System.out.print(ships[row][col].getShipType().substring(0, 1) + " ");
				}
			}
			System.out.println(); // Move to the next line after printing the row
		}
	}
	
}
