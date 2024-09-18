/*
 * @author Cheng Erxi & Jiayi Wang
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

	Ocean ocean;
	Ship ship;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}

	@Test
	void testGetLength() {
		ship = new Battleship();
		assertEquals(4, ship.getLength());

		//TODO
		//More tests
		//Test while the ship type is Cruiser
		Cruiser ship_2 = new Cruiser();
		assertEquals(3, ship_2.getLength());

		//Test while the ship type is Destroyer
		Destroyer ship_3 = new Destroyer();
		assertEquals(2, ship_3.getLength());

		//Test while the ship type is Submarine
		Submarine ship_4 = new Submarine();
		assertEquals(1, ship_4.getLength());

		//Test while is the getlength() remain the same after being shot
		ship_2.placeShipAt(5, 5, true, ocean);
		assertTrue(ship_2.shootAt(5, 5));
		assertEquals(3, ship_2.getLength());

		//Test while is the getlength() remain the same after the sinking
		assertTrue(ship_2.shootAt(5, 4));
		assertTrue(ship_2.shootAt(5, 3));
		assertTrue(ship_2.isSunk());
		assertEquals(3, ship_2.getLength());
	}

	@Test
	void testGetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		
		//TODO
		//More tests
		//Test while the ship was not placed horizontally (placed vertically)
		Ship destroyer = new Destroyer();
		int row_2 = 7;
		int column_2 = 8;
		destroyer.placeShipAt(row_2, column_2, false, ocean);
		assertEquals(row_2, destroyer.getBowRow());
		//Test the new bow row after being placed to a new place AGAIN (multiple placed test)
		destroyer.placeShipAt(row_2, column_2, false, ocean);
		destroyer.placeShipAt(5, 6, false, ocean);
		assertEquals(5,destroyer.getBowRow());

		//Test if the position will remain same after being shot
		assertTrue(destroyer.shootAt(destroyer.getBowRow(),destroyer.getBowColumn()));
		assertEquals(5,destroyer.getBowRow());

		//Test if the position will remain same after sinking
		assertTrue(destroyer.shootAt(destroyer.getBowRow()-1,destroyer.getBowColumn()));
		assertTrue(destroyer.isSunk());
		assertEquals(5,destroyer.getBowRow());
	}

	@Test
	void testGetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());	
		
		//TODO
		//More tests
		//Test while the ship was not placed horizontally (placed vertically)
		Ship destroyer = new Destroyer();
		int row_2 = 7;
		int column_2 = 8;
		destroyer.placeShipAt(row_2, column_2, false, ocean);
		assertEquals(column_2, destroyer.getBowColumn());
		//Test the new bow row after being placed to a new place AGAIN (multiple placed test)
		destroyer.placeShipAt(row_2, column_2, false, ocean);
		destroyer.placeShipAt(5, 6, false, ocean);
		assertEquals(6,destroyer.getBowColumn());

		//Test if the position will remain same after being shot
		assertTrue(destroyer.shootAt(destroyer.getBowRow(),destroyer.getBowColumn()));
		assertEquals(6,destroyer.getBowColumn());

		//Test if the position will remain same after sinking
		assertTrue(destroyer.shootAt(destroyer.getBowRow()-1,destroyer.getBowColumn()));
		assertTrue(destroyer.isSunk());
		assertEquals(6,destroyer.getBowColumn());
	}

	@Test
	void testGetHit() {
		ship = new Battleship();
		boolean[] hits = new boolean[4];
		assertArrayEquals(hits, ship.getHit());
		assertFalse(ship.getHit()[0]);
		assertFalse(ship.getHit()[1]);
		
		//TODO
		//More tests
		//Test after being shoot
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(ship.shootAt(row,column));
		assertTrue(ship.getHit()[0]);

		//Test after the ship was sunk, in other word, every part of the ship was shot
		assertTrue(ship.shootAt(row,column-1));
		assertTrue(ship.shootAt(row,column-2));
		assertTrue(ship.shootAt(row,column-3));
		assertTrue(ship.isSunk());
		assertTrue(ship.getHit()[0]&&ship.getHit()[1]&&ship.getHit()[2]&&ship.getHit()[3]);
	}
	@Test
	void testGetShipType() {
		ship = new Battleship();
		assertEquals("battleship", ship.getShipType());
		
		//TODO
		//More tests
		//Test all other ship type
		Cruiser cruiser = new Cruiser();
		Destroyer destroyer = new Destroyer();
		Submarine submarine = new Submarine();
		assertEquals("cruiser", cruiser.getShipType());
		assertEquals("destroyer", destroyer.getShipType());
		assertEquals("submarine", submarine.getShipType());

		//Test if the output remain the same after being shot
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(ship.shootAt(row,column));
		assertEquals("battleship",ship.getShipType());

		//Test if the output remain same after the ship sunk, in other word, every part of the ship was shot
		assertTrue(ship.shootAt(row,column-1));
		assertTrue(ship.shootAt(row,column-2));
		assertTrue(ship.shootAt(row,column-3));
		assertTrue(ship.isSunk());
		assertEquals("battleship",ship.getShipType());
	}
	
	@Test
	void testIsHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(battleship.isHorizontal());
		
		//TODO
		//More tests
		//Test if the ship was initially vertically placed
		Ship destroyer = new Destroyer();
		destroyer.placeShipAt(5, 5, false, ocean);
		assertFalse(destroyer.isHorizontal());

		//Test after a vertically placed ship was replaced to horizontal (Replacement test)
		destroyer.placeShipAt(5, 5, true, ocean);
		assertTrue(destroyer.isHorizontal());

		//Test after the ship being shot, the horizontal/vertical should remain the same
		assertTrue(battleship.shootAt(row,column));
		assertTrue(battleship.isHorizontal());

		//Test if the output remain same after the ship sunk, in other word, every part of the ship was shot
		assertTrue(battleship.shootAt(row,column-1));
		assertTrue(battleship.shootAt(row,column-2));
		assertTrue(battleship.shootAt(row,column-3));
		assertTrue(battleship.isHorizontal());
	}
	
	@Test
	void testSetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowRow(row);
		assertEquals(row, battleship.getBowRow());
		
		//TODO
		//Test the updated the bow row (set the bow row for multiple time then check if the value are updated)
		battleship.setBowRow(row);
		assertEquals(row, battleship.getBowRow());
		battleship.setBowRow(7);
		assertEquals(7, battleship.getBowRow());
		//Test if the return value remain the same after the ship being shot
		battleship.setBowRow(row);
		battleship.setBowColumn(column);
		battleship.setHorizontal(true);
		assertTrue(battleship.shootAt(row,column));
		assertEquals(row, battleship.getBowRow());
		//Test if the output remain same after the ship sunk, in other word, every part of the ship was shot
		assertTrue(battleship.shootAt(row,column-1));
		assertTrue(battleship.shootAt(row,column-2));
		assertTrue(battleship.shootAt(row,column-3));
		assertEquals(row, battleship.getBowRow());
	}

	@Test
	void testSetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());
		
		//TODO
		//More tests
		//Test the updated the bow column (set the bow column for multiple time then check if the value are updated)
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());
		battleship.setBowColumn(7);
		assertEquals(7, battleship.getBowColumn());
		//Test if the return value remain the same after the ship being shot
		battleship.setBowColumn(column);
		battleship.setBowRow(row);
		battleship.setHorizontal(true);
		assertTrue(battleship.shootAt(row,column));
		assertEquals(column, battleship.getBowColumn());
		//Test if the output remain same after the ship sunk, in other word, every part of the ship was shot
		assertTrue(battleship.shootAt(row,column-1));
		assertTrue(battleship.shootAt(row,column-2));
		assertTrue(battleship.shootAt(row,column-3));
		assertEquals(column, battleship.getBowColumn());
	}

	@Test
	void testSetHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setHorizontal(horizontal);
		assertTrue(battleship.isHorizontal());
		
		//TODO
		//More tests
		//Test the updated the horizontal/vertical (set the horizontal/vertical for multiple time then check if the value are updated)
		battleship.setHorizontal(horizontal);
		assertTrue(battleship.isHorizontal());
		battleship.setHorizontal(false);
		assertFalse(battleship.isHorizontal());
		//Test if the return value remain the same after the ship being shot
		battleship.setBowColumn(column);
		battleship.setBowRow(row);
		battleship.setHorizontal(true);
		assertTrue(battleship.shootAt(row,column));
		assertTrue(battleship.isHorizontal());
		//Test if the output remain same after the ship sunk, in other word, every part of the ship was shot
		assertTrue(battleship.shootAt(row,column-1));
		assertTrue(battleship.shootAt(row,column-2));
		assertTrue(battleship.shootAt(row,column-3));
		assertTrue(battleship.isHorizontal());
	}

	@Test
	void testOkToPlaceShipAt() {
		
		//test when other ships are not in the ocean
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok = battleship.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship here.");
		
		//TODO
		//More tests
		//Test when other ships are in the ocean but far away from each other
		Ship submarine = new Submarine();
		submarine.setBowColumn(9);
		submarine.setBowRow(9);
		submarine.setHorizontal(true);
		ok=submarine.okToPlaceShipAt(9, 9, true, ocean);
		assertTrue(ok, "OK to place ship here.");
		//Test when other ships are touch each other (horizontally)
		battleship.setBowColumn(4);
		battleship.setBowRow(0);
		battleship.setHorizontal(true);
		battleship.placeShipAt(0,4,true,ocean);
		ok = submarine.okToPlaceShipAt(0, 5, true, ocean);
		assertFalse(ok,"not ok as the ships are horizontally touched.");
		//Test when other ships are touch each other (vertically)
		submarine.setBowColumn(2);
		submarine.setBowRow(1);
		submarine.setHorizontal(true);
		ok = submarine.okToPlaceShipAt(1, 2, true, ocean);
		assertFalse(ok,"Not ok as the ships are vertically touched.");
		//Test when other ships are touch each other (diagonally)
		submarine.setBowColumn(0);
		submarine.setBowRow(1);
		submarine.setHorizontal(true);
		ok = submarine.okToPlaceShipAt(1, 0, true, ocean);
		assertFalse(ok,"Not ok as the ships are diagonally touched.");
		//Test when overlap with other ship
		submarine.setBowColumn(4);
		submarine.setBowRow(0);
		submarine.setHorizontal(true);
		ok = submarine.okToPlaceShipAt(0, 4, true, ocean);
		assertFalse(ok,"Not ok as the ships are overlap.");
	}
	
	@Test
	void testOkToPlaceShipAtAgainstOtherShipsOneBattleship() {
		
		//test when other ships are in the ocean
		
		//place first ship
		Battleship battleship1 = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok1 = battleship1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		battleship1.placeShipAt(row, column, horizontal, ocean);

		//test second ship
		Battleship battleship2 = new Battleship();
		row = 1;
		column = 4;
		horizontal = true;
		boolean ok2 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
		
		//TODO
		//More tests
		//test the third ship, which is not touched or overlap with any ship
		row = 9;
		column = 9;
		horizontal = true;
		boolean ok3 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok3, "OK to place ship here.");

		//test the fourth ship, which is overlap with the first one
		row = 0;
		column = 3;
		horizontal = true;
		boolean ok4 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok4, "Not OK to place ship overlap with other.");
	}

	@Test
	void testPlaceShipAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		assertEquals(column, battleship.getBowColumn());
		assertTrue(battleship.isHorizontal());
		
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals(battleship, ocean.getShipArray()[0][1]);
		

		//TODO
		//More tests
		//Test the value of ship array after a replacement (place update multiply)
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals(battleship, ocean.getShipArray()[0][1]);
		row=9;
		column=9;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(battleship, ocean.getShipArray()[9][8]);

		//Test while placing a new ship in the ocean (test while there are multiple ship in the ocean)
		Ship submarine = new Submarine();
		row=3;
		column=3;
		submarine.placeShipAt(row, column, horizontal, ocean);
		assertEquals(submarine, ocean.getShipArray()[3][3]);
	}

	@Test
	void testShootAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 9;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(battleship.shootAt(1, 9));
		boolean[] hitArray0 = {false, false, false, false};
		assertArrayEquals(hitArray0, battleship.getHit());
		
		//TODO
		//More tests
		//Test while shooting at empty target
		assertFalse(battleship.shootAt(0,0)); //(0,0) is empty

		//Test while shooting at the ship (not sinking yet)
		assertTrue(battleship.shootAt(0, 9));
		assertTrue(battleship.shootAt(0, 8));
		assertTrue(battleship.shootAt(0, 7));
		//Test while shooting at the ship that was already hit (but not sinking yet)
		assertTrue(battleship.shootAt(0, 9));
		assertTrue(battleship.shootAt(0, 8));
		assertTrue(battleship.shootAt(0, 7));
		//Test while shooting at the sunk ship
		assertTrue(battleship.shootAt(0, 6));
		assertTrue(battleship.isSunk());
		assertFalse(battleship.shootAt(0, 9));
		assertFalse(battleship.shootAt(0, 8));
		assertFalse(battleship.shootAt(0, 7));
		assertFalse(battleship.shootAt(0, 6));
	}
	
	@Test
	void testIsSunk() {
		
		Ship submarine = new Submarine();
		int row = 3;
		int column = 3;
		boolean horizontal = true;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(submarine.isSunk());
		assertFalse(submarine.shootAt(5, 2));
		assertFalse(submarine.isSunk());
		
		//TODO
		//More tests
		//Test while the submarine was hit
		assertFalse(submarine.isSunk());
		assertTrue(submarine.shootAt(3, 3));
		assertTrue(submarine.isSunk());
		//Test while a ship was partially hit
		Ship battleship = new Battleship();
		battleship.placeShipAt(9, 9, horizontal, ocean);
		assertTrue(battleship.shootAt(9, 9));
		assertTrue(battleship.shootAt(9, 8));
		assertTrue(battleship.shootAt(9, 7));
		assertFalse(battleship.isSunk());
		//Test while a ship was hit by all of its part
		assertTrue(battleship.shootAt(9, 6));
		assertTrue(battleship.isSunk());
	}

	@Test
	void testToString() {
		
		Ship battleship = new Battleship();
		assertEquals("x", battleship.toString());
		
		int row = 9;
		int column = 1;
		boolean horizontal = false;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.shootAt(9, 1);
		assertEquals("x", battleship.toString());
		
		//TODO
		//More tests
		//Test while the ship was partially hit
		assertTrue(battleship.shootAt(9, 1));
		assertTrue(battleship.shootAt(8, 1));
		assertTrue(battleship.shootAt(7, 1));
		assertFalse(battleship.isSunk());
		assertEquals("x", battleship.toString());
		//Test while the ship was hit by all the part
		assertTrue(battleship.shootAt(6, 1));
		assertTrue(battleship.isSunk());
		assertEquals("s", battleship.toString());
	}
	@Test
	void testIsHitAt() {
		Battleship battleship = new Battleship();
		//Test while hit at the body of the ship
		battleship.placeShipAt(4,4,true,ocean);
		ocean.shootAt(4,1);
		assertTrue(battleship.isHitAt(4,1));

		//Test while checking a un-hit part of the ship
		assertFalse(battleship.isHitAt(4,4));

		//Test while the ship was hit to sink
		ocean.shootAt(4,1);
		ocean.shootAt(4,2);
		ocean.shootAt(4,3);
		ocean.shootAt(4,4);
		assertTrue(battleship.isHitAt(4,2));

		//Test while checking an "emptysea"
		assertFalse(battleship.isHitAt(9,9));
	}

}
