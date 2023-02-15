package sample;
import java.util.*;

public class Game {
    //initialize variables
    String[][] board = new String[10][10];
    String[][] enemy = new String[10][10];
    String[][] enemyDisplay = new String[10][10];
    String[] ships = {"B", "c", "S", "C", "D"};
    String[] shipNames = {"Battleship", "Cruiser", "Submarine", "Carrier", "Destroyer"};
    int[] counts = {4, 3, 3, 5, 2};
    boolean[] playerExist = {true, true, true, true, true};
    boolean[] enemyExist = {true, true, true, true, true};
    //B(attleship) = 4, c(ruiser) = 3, S(ubmarine) = 3, C(arrier) = 5, D(estroyer) = 2

    public Game() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //fill all boards with empty spaces
                board[i][j] = " ";
                enemy[i][j] = " ";
                enemyDisplay[i][j] = " ";
            }
        } //generate random boards for player and enemy
        generateBoard(board);
        generateBoard(enemy);
    }

    public void runSim() {
        Scanner in = new Scanner(System.in);
        int count = 5;
        int eneCount = 5;
        int turn = 1;
        System.out.println("Turn " + turn + "\n");
        System.out.println("Enemy Board");
        printBoard(enemyDisplay);
        System.out.println("\nPlayer Board");
        printBoard(board);
        //while either side has more than 1 ship, continue loop
        while (count > 0 || eneCount > 0) {
            System.out.print("\nPlayer Turn: Enter an x and y value (ex. 1 1) ");
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            while (x < 0 || x > 9 || y < 0 || y > 9 || !enemyDisplay[x][y].equals(" ")) {
                //while x or y is greater than the bounds of the board or the area has already been hit, ask for another input
                System.out.print("\nPlease enter a valid x and y value (ex. 1 1) ");
                x = in.nextInt() - 1;
                y = in.nextInt() - 1;
            }
            if (!turn(enemy, x, y)) {
                enemyDisplay[x][y] = "/";
            } else {
                enemyDisplay[x][y] = "X";
            } enemyExist = checkBoard(enemy, enemyExist);
            eneCount = 0;
            //check how many enemy ships remain
            for (int i = 0; i < 5; i++) {
                if (enemyExist[i]) {
                    eneCount++;
                }
            } if (eneCount == 0) {
                //if there are no enemy ships, break the loop early
                break;
            }
            x = (int)(Math.random() * 10);
            y = (int)(Math.random() * 10);
            //randomly generate coordinates for enemy turn, same conditions as player choice
            while (board[x][y].equals("X") || board[x][y].equals("/")) {
                x = (int)(Math.random() * 10);
                y = (int)(Math.random() * 10);
            }
            System.out.println("\nEnemy Turn: " + x + " " + y);
            if (turn(board, x, y)) {
                board[x][y] = "X";
            } else {
                board[x][y] = "/";
            } playerExist = checkBoard(board, playerExist);
            count = 0;
            //count how many player ships are left
            for (int i = 0; i < 5; i++) {
                if (playerExist[i]) {
                    count++;
                }
            }
            turn++;
            System.out.println("------------\n\nTurn " + turn);
            System.out.println("\nEnemy Board");
            printBoard(enemyDisplay);
            System.out.println("\nPlayer Board");
            printBoard(board);
        } if (count > 0) {
            //if player has more than one ship left, player wins
            System.out.println("Player win!");
        } else {
            //otherwise, enemy wins
            System.out.println("Enemy win!");
        } in.close();
    }

    public boolean[] checkBoard(String[][] tempBoard, boolean[] tempLine) {
        boolean[] tempExist = {false, false, false, false, false};
        //initially see no ship
        for (int i = 0; i < tempBoard.length; i++) {
            for (int k = 0; k < tempBoard[0].length; k++) {
                if (tempBoard[i][k].equals("B")) {
                    tempExist[0] = true;
                } else if (tempBoard[i][k].equals("c")) {
                    tempExist[1] = true;
                } else if (tempBoard[i][k].equals("S")) {
                    tempExist[2] = true;
                } else if (tempBoard[i][k].equals("C")) {
                    tempExist[3] = true;
                } else if (tempBoard[i][k].equals("D")) {
                    tempExist[4] = true;
                } // if letter corresponds to ship type, set parallel array tempExist to true to show it exists
            }
        } for (int i = 0; i < 5; i++) {
            if (tempExist[i] != tempLine[i]) {
                System.out.println(shipNames[i] + " has been destroyed!");
                //if tempExist of this type of ship doesn't exist and doesn't line up with initial tempLine, say the ship has been destroyed
            }
        }
        return tempExist;
    }

    public boolean turn(String[][] tempBoard, int x, int y) {
        if (!tempBoard[x][y].equals(" ")) {
            //if board isn't empty, print hit and swap with X
            tempBoard[x][y] = "X";
            System.out.println("HIT");
            return true;
        } else {
            //otherwise print miss and swap with /
            tempBoard[x][y] = "/";
            System.out.println("MISS");
            return false;
        }
    }


    public void printBoard(String[][] tempBoard) {
        //board coordinates on x axis
        System.out.println("    1 2 3 4 5 6 7 8 9 10 X");
        for (int i = 0; i < tempBoard[0].length; i++) {
            //board coordinates on y axis
            if (i < 9) {
                System.out.print(i + 1 + "   ");
            } else {
                System.out.print(i + 1 + "  ");
            }
            for (int j = 0; j < tempBoard.length; j++) {
                //print value of tempBoard
                System.out.print(tempBoard[j][i] + " ");
            } System.out.println();
        } System.out.println("Y");
    }

    public String[][] getBoard() {
        return board;
    }

    public String[][] getEnemy() {
        return enemy;
    }

    public String[][] getEnemyDisplay() {
        return enemyDisplay;
    }

    public void generateBoard(String[][] tempBoard) {
        for (int i = 0; i < 5; i++) {
            //repeats for all five ship types
            boolean placed = false;
            while (!placed) {
                //repeats while the ship has not been placed
                int x = (int)(Math.random() * 10);
                int y = (int)(Math.random() * 10);
                //random coordinates between 0-9
                boolean exists = false;
                if (x < tempBoard.length - counts[i]) {
                    //horizontal check
                    for (int j = x; j < x + counts[i]; j++) {
                        if (tempBoard[j][y].equals(" ") == false) {
                            exists = true;
                        } //if following spaces are occupied, exists equals true
                    } if (exists == false) {
                        //if there exists no ship in the following spaces...
                        for (int k = x; k < x + counts[i]; k++) {
                            tempBoard[k][y] = ships[i];
                            //append ship type to spaces
                        }
                        placed = true;
                        //note placed as true, move on to next ship
                    }
                } else if (y < tempBoard[0].length - counts[i]) {
                    //if horizontal not available, vertical check
                    //same thing for vertical as horizontal, but on y
                    for (int j = y; j < y + counts[i]; j++) {
                        if (tempBoard[x][j].equals(" ") == false) {
                            exists = true;
                        }
                    } if (exists == false) {
                        for (int k = y; k < y + counts[i]; k++) {
                            tempBoard[x][k] = ships[i];
                        }
                        placed = true;
                    }
                }
            }
        }
    }
}
