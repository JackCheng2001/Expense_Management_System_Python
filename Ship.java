/**
 * describes characteristics common to all ships
 * @author Cheng Erxi & Jiayi Wang
 */
public abstract class Ship {

	//instance variables
	/**
	 * row that contains the bow (front part of ship)
	 */
	private int bowRow;
	
	/**
	 * column that contains the bow (front part of ship)
	 */
	private int bowColumn;
	
	/**
	 * length of ship
	 */
	private int length;
	
	/**
	 * boolean represents whether the ship is placed horizontally or vertically
	 */
	private boolean horizontal;
	
	/**
	 * array of booleans that indicate whether that part of ship bas been hit or not
	 */
	private boolean[] hit;
	
	
	//constructor
	
	/**
	 * create a ship based on the length
	 * initialize the hit array based on the length
	 * @param length
	 */
	public Ship(int length) {
		this.length = length;
		
		hit = new boolean[length];
		
		for (int i = 0; i < length; i++) {
			
			hit[i] = false;
		}
	}


	//method 
	/**
	 * @return the bowRow
	 */
	public int getBowRow() {
		return bowRow;
	}


	/**
	 * @param bowRow the bowRow to set
	 */
	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}


	/**
	 * @return the bowColumn
	 */
	public int getBowColumn() {
		return bowColumn;
	}


	/**
	 * @param bowColumn the bowColumn to set
	 */
	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}


	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}


	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}


	/**
	 * @return the horizontal
	 */
	public boolean isHorizontal() {
		return horizontal;
	}


	/**
	 * @param horizontal the horizontal to set
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}


	/**
	 * @return the hit
	 */
	public boolean[] getHit() {
		return hit;
	}


	/**
	 * @param hit the hit to set
	 */
	public void setHit(boolean[] hit) {
		this.hit = hit;
	}
	
	/**
	 * force all types of ships to implement this method 
	 * return type of ship as a String
	 * @return type of ship
	 */
	public abstract String getShipType();

	/**
	 * return true if it is okay to put ship of this length with its bow in this location
	 * @param row of bow
	 * @param column of bow 
	 * @param horizontal 
	 * @param ocean
	 * @return true if can be placed 
	 */
	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		//initialize the length of the ship
		int shipLength = this.length;
		int i=0;
		int j=0;

			
		//Check if out of bound
		if(column > 9 || row > 9) {
			return false;
		}

		if(horizontal)
		{
			if(column<shipLength-1)
			{
				return false;
			}
		}
		else
		{
			if(row<shipLength-1)
			{
				return false;
			}
		}

		//check if the ship is touch with others
		if(horizontal){
			for(i=Math.max(0,row-1); i<=Math.min(row+1,9);i++){
				for(j=Math.max(0,column-length);j<=Math.min(column+1,9);j++){
					if(j>column-length&&j<=column&&i==row){
						continue;
					}
					if(ocean.isOccupied(i,j)){
						return false;
					}
				}
			}
		}
		else {
			for(i=Math.max(0,row-length); i<=Math.min(9,row+1);i++){
				for(j=Math.max(0,column-1);j<=Math.min(column+1,9);j++){
					if(i<=row&&j==column&&i>row-length){
						continue;
					}
					if(ocean.isOccupied(i,j)){
						return false;
					}
				}
			}
		}

		//check if the ship is overlapping with others or touching other ships
		for(i = 0; i < shipLength; i++) {
			
			int checkRow = 0;
			
			if (horizontal) {
				//if the ship is placed horizontally, the row stays the same
				checkRow = row;
				
			} else {
				//if the ship is placed vertically, then increment row positions
				checkRow = row + i;
			}
			
			int checkCol = 0;
			
			if (horizontal) {
				
				//if the ship is placed horizontally, then increment column positions
				checkCol = column + i;
				
			} else {
				//if the ship is placed vertically, the column stays the same
				checkCol = column;
			}
			
			//check if the locations of the ship contain a ship already
			if (ocean.isOccupied(checkRow, checkCol)) {
				return false;
			}
			
			//check if adjacent ships (vertically, horizontally, diagonally) exists
			//iterate over rows adjacent to the current position (checkRow)
			for ( int r = Math.max(0, checkRow - 1); r <= Math.min(9, checkRow + 1); r++) {
				//iterate over columns adjacent to current position (checkCol)
				for ( int c = Math.max(0, checkCol - 1); c <= Math.min(9, checkCol + 1); c++) {
					//check if the adjacent positions are occupied
					if (ocean.isOccupied(r, c)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * puts the ship in the ocean
	 * @param row of bow
	 * @param column of bow
	 * @param horizontal or vertical
	 * @param ocean
	 */
	public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		//initialize the bow row, bow column, and horizontal 
		this.bowColumn = column;
		this.bowRow = row;
		this.horizontal = horizontal; 
		
		//store the 10x10 array of Ships in the currentShips variable
		Ship[][] currentShips = ocean.getShipArray();
		
		//iterate over the length of the ship to place the ship
		ocean.getShipArray()[row][column] = this;
			for (int i = 0; i < this.length; i++) {
				//condition1: if the ship is placed horizontally
				if (horizontal == true) {
					ocean.getShipArray()[row][column - i] = this;
					//condition2: if the ship is placed vertically
				} else {
					ocean.getShipArray()[row - i][column] = this;
				}
			}


	}
	
	/**
	 * If a part of the ship occupies the given row and column, and the ship hasn’t been sunk
	 * mark that part of the ship as “hit” and return true
	 * @param row of the intended hit
	 * @param column of the intended hit
	 * @return
	 */
	public boolean shootAt(int row, int column) {
		//condition1: the ship is sunk, return false
		if (this.isSunk() == true) {
			return false;
		
		//condition2: the ship is not sunk, return true
		} else {
			
			//condition1: when the ship is placed horizontally 
			if (this.horizontal == true) {
				
				//if the row is not on the same row as ship is, return false
				if(row != this.bowRow) {
					return false;
				} else {
					int hitCheckColumn = this.bowColumn - column + 1;
					
					//check if the column position is matching with the ship position
					if(hitCheckColumn <= this.length && hitCheckColumn > 0) {
						
						//change the hit index of the (row,column) to true
						hit[this.bowColumn - column] = true;
						
						//return true based on result
						return true;
					} else {
						return false;
					}
				} 
			
			//condition2: when the ship is placed vertically
			} else {
				
				//if the column is not on the same column as the ship is, return false
				if(column != this.bowColumn) {
					return false;
				} else {
					int hitCheckRow = this.bowRow - row + 1;
					
					//check if the row position is matching with the ship position
					if (hitCheckRow <= this.length && hitCheckRow > 0) {
						
						//change the hit index of the (row,column) to true
						hit[this.bowRow - row] = true;
						
						//return true based on result
						return true;
					} else {
						return false;
					}	
				}				
			}			
		}		
	}
	
	/**
	 * return true if every part of the ship has been hit, false otherwise
	 * @return the result
	 */
	public boolean isSunk() {
		//iterate over the length of the boolean hit array
		for (int i = 0; i<hit.length; i++) {
			//if one of the element is not hit, return false
			if (hit[i] == false) {
				return false;
			}
		//return true if all the ships are hit
		}
		return true;
	}
	
	/**
	 * return a single-character String to use in the Ocean's print method
	 * return "s" if the ship has been sunk and "x" if it has not been sunk
	 */
	public String toString() {
		// If the part of the ship has been hit, mark with 'x', otherwise with '.'
		// for example, an array of booleans indicating which parts have been hit.
		if (this.isSunk()) {

			return "s";  // All parts have been hit, ship is sunk
		} else {
			for (boolean hit : this.hit) {
				if (hit) {

					return "x";  // Part of the ship has been hit
				}
			}
		}

		return "x";  // No part has been hit
	}

	public boolean isHitAt(int row, int column) {
		// First, determine if this part of the ship is at the specified location.
		// This will depend on the orientation of the ship.
		if (this.horizontal) {
			if (this.bowRow == row && this.bowColumn >= column &&column > this.bowColumn - this.length) {
				// If the ship is horizontal, check if the column falls within the ship's range.
				return hit[this.bowColumn-column];
			}
		} else {
			if (this.bowColumn == column && this.bowRow >= row && row > this.bowRow - this.length) {
				// If the ship is vertical, check if the row falls within the ship's range.
				return hit[this.bowRow-row];
			}
		}
		return false; // If the location is not part of the ship at all, return false.
	}
}



	
