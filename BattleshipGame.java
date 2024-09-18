/*
 * @author Cheng Erxi & Jiayi Wang
 */

import java.util.Scanner;

public class BattleshipGame {

    public static void main(String[] args) {
        Ocean ocean = new Ocean();
        Scanner scanner = new Scanner(System.in);
        ocean.placeAllShipsRandomly();

        System.out.println("Welcome to Battleship!");

        while (!ocean.isGameOver()) {
            printOcean(ocean);

            System.out.println("Enter row, column:");
            String input = scanner.nextLine();
            int[] shot = parseShot(input);

            if (shot != null) {
                boolean hit = ocean.shootAt(shot[0], shot[1]);
                if (hit) {
                    System.out.println("Hit!");
                    if (ocean.getShipArray()[shot[0]][shot[1]].isSunk()) {
                        System.out.println("You just sank a " + ocean.getShipArray()[shot[0]][shot[1]].getShipType());
                    }
                } else {
                    System.out.println("Miss.");

                }
            } else {
                System.out.println("Invalid coordinates. Try again.");
            }
        }

        System.out.println("Game over! You took " + ocean.getShotsFired() + " shots to sink all ships.");
        scanner.close();
    }

    private static void printOcean(Ocean ocean) {
        // The Ocean class's print() method would be responsible for printing the ocean grid.
        ocean.print();
    }

    private static int[] parseShot(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) return null;

        try {
            int row = Integer.parseInt(parts[0].trim());
            int column = Integer.parseInt(parts[1].trim());
            return new int[]{row, column};
        } catch (NumberFormatException e) {
            return null;
        }
    }
}