/*
 * @author Cheng Erxi & Jiayi Wang
 */
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

	Ocean ocean;
	
	static int NUM_BATTLESHIPS = 1;
	static int NUM_CRUISERS = 2;
	static int NUM_DESTROYERS = 3;
	static int NUM_SUBMARINES = 4;
	static int OCEAN_SIZE = 10;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}
	
	@Test
	void testEmptyOcean() {
		
		//tests that all locations in the ocean are "empty"
		
		Ship[][] ships = ocean.getShipArray();
		System.out.println(ships.length);
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				
				assertEquals("empty", ship.getShipType());
			}
		}
		
		assertEquals(0, ships[0][0].getBowRow());
		assertEquals(0, ships[0][0].getBowColumn());
		
		assertEquals(5, ships[5][5].getBowRow());
		assertEquals(5, ships[5][5].getBowColumn());
		
		assertEquals(9, ships[9][0].getBowRow());
		assertEquals(0, ships[9][0].getBowColumn());
	}
	
	@Test
	void testPlaceAllShipsRandomly() {
		
		//tests that the correct number of each ship type is placed in the ocean
		
		ocean.placeAllShipsRandomly();

		Ship[][] ships = ocean.getShipArray();
		ArrayList<Ship> shipsFound = new ArrayList<Ship>();
		
		int numBattlehips = 0;
		int numCruisers = 0;
		int numDestroyers = 0;
		int numSubmarines = 0;
		int numEmptySeas = 0;
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				if (!shipsFound.contains(ship)) {
					shipsFound.add(ship);
				}
			}
		}
		
		for (Ship ship : shipsFound) {
			if ("battleship".equals(ship.getShipType())) {		
				numBattlehips++;
			} else if ("cruiser".equals(ship.getShipType())) {
				numCruisers++;
			} else if ("destroyer".equals(ship.getShipType())) {
				numDestroyers++;
			} else if ("submarine".equals(ship.getShipType())) {
				numSubmarines++;
			} else if ("empty".equals(ship.getShipType())) {
				numEmptySeas++;
			}
		}
		
		assertEquals(NUM_BATTLESHIPS, numBattlehips);
		assertEquals(NUM_CRUISERS, numCruisers);
		assertEquals(NUM_DESTROYERS, numDestroyers);
		assertEquals(NUM_SUBMARINES, numSubmarines);
		
		//calculate total number of available spaces and occupied spaces
		int totalSpaces = OCEAN_SIZE * OCEAN_SIZE; 
		int occupiedSpaces = (NUM_BATTLESHIPS * 4)
				+ (NUM_CRUISERS * 3)
				+ (NUM_DESTROYERS * 2)
				+ (NUM_SUBMARINES * 1);
		
		//test number of empty seas, each with length of 1
		assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
	}

	@Test
	void testIsOccupied() {

		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(1, 5));
		
		//TODO
		//More tests
		//Test a place that is occupied by ship (However by the body of ship not the bow)
		assertTrue(ocean.isOccupied(0, 5));

		//Test a place that is not occupied by ship
		assertFalse(ocean.isOccupied(9, 9));

		//Test a place that used to placed a ship however sunk
		assertTrue(destroyer.shootAt(1,5));
		assertTrue(destroyer.shootAt(0,5));
		assertTrue(destroyer.isSunk());
		assertTrue(ocean.isOccupied(1,5));
	}

	@Test
	void testShootAt() {
	
		assertFalse(ocean.shootAt(0, 1));
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		
		//TODO
		//More tests
		//Test while shooting at a empty target
		assertFalse(ocean.shootAt(9, 9));

		//Test while shooting after it sunk
		assertTrue(destroyer.isSunk());
		assertFalse(ocean.shootAt(0, 5));
	}

	@Test
	void testGetShotsFired() {
		
		//should be all false - no ships added yet
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(3, 3));
		assertFalse(ocean.shootAt(9, 9));
		assertEquals(4, ocean.getShotsFired());
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(6, ocean.getShotsFired());
		
		//TODO
		//More tests
		//Test while shooting at the target (not sunk yet)
		Ship cruiser = new Cruiser();
		cruiser.placeShipAt(5, 4, true, ocean);
		assertTrue(ocean.shootAt(5,4));
		assertTrue(ocean.shootAt(5,3));
		assertFalse(cruiser.isSunk());
		assertEquals(8,ocean.getShotsFired());
		//Test while shooting at the target till it sink
		assertTrue(ocean.shootAt(5,2));
		assertTrue(cruiser.isSunk());
		assertEquals(9,ocean.getShotsFired(),"whether the ship sink or not doesn't effect the counting of shot fire");
	}

	@Test
	void testGetHitCount() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		
		//TODO
		//More tests
		//Test after shooting at empty target
		assertFalse(ocean.shootAt(9, 9));
		assertFalse(ocean.shootAt(9, 8));
		assertFalse(ocean.shootAt(9, 7));
		assertFalse(ocean.shootAt(9, 6));
		assertEquals(1, ocean.getHitCount(),"Hit count remain since all the hit was missed");

		//Test after shooting at the ship till it sink
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(2, ocean.getHitCount(),"As expected, whether the ship sink does not effect the counting");
	}
	
	@Test
	void testGetShipsSunk() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());
		
		//TODO
		//More tests
		//Test while another battleship sunk
		Battleship battleship = new Battleship();
		row=9;
		column=9;
		battleship.placeShipAt(row,column,horizontal,ocean);
		assertTrue(ocean.shootAt(9, 9));
		assertTrue(ocean.shootAt(8, 9));
		assertTrue(ocean.shootAt(7, 9));
		assertTrue(ocean.shootAt(6, 9));
		assertTrue(battleship.isSunk());
		assertEquals(5, ocean.getHitCount());
		assertEquals(1, ocean.getShipsSunk());

		//(Update Test)Test while another ship was hit but not yet sunk, then after it sunk, check the value update.
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(6,6,false,ocean);
		assertTrue(ocean.shootAt(6, 6));
		assertTrue(ocean.shootAt(5, 6));
		assertFalse(cruiser.isSunk());
		assertEquals(7, ocean.getHitCount());
		assertEquals(1, ocean.getShipsSunk());
		assertTrue(ocean.shootAt(4, 6));
		assertTrue(cruiser.isSunk());
		assertEquals(8, ocean.getHitCount());
		assertEquals(2, ocean.getShipsSunk());
	}

	@Test
	void testGetShipArray() {
		
		Ship[][] shipArray = ocean.getShipArray();
		assertEquals(OCEAN_SIZE, shipArray.length);
		assertEquals(OCEAN_SIZE, shipArray[0].length);
		
		assertEquals("empty", shipArray[0][0].getShipType());
		
		//TODO
		//More tests
		//Test while trying to get a ship on the ocean
		Battleship battleship = new Battleship();
		int row=9;
		int column=9;
		boolean horizontal=false;
		battleship.placeShipAt(row,column,horizontal,ocean);
		assertEquals("battleship", shipArray[9][9].getShipType());
		assertEquals("battleship", shipArray[8][9].getShipType());
		assertEquals("battleship", shipArray[7][9].getShipType());
		assertEquals("battleship", shipArray[6][9].getShipType());

		//Test while trying to get a ship which was sunk
		assertTrue(ocean.shootAt(9,9));
		assertTrue(ocean.shootAt(8,9));
		assertTrue(ocean.shootAt(7,9));
		assertTrue(ocean.shootAt(6,9));
		assertTrue(battleship.isSunk());
		assertEquals("battleship", shipArray[9][9].getShipType());
	}

}
