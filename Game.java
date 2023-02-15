package sample;
import java.util.*;

public class Game {
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
                board[i][j] = " ";
                enemy[i][j] = " ";
                enemyDisplay[i][j] = " ";
            }
        } generateBoard(board);
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
        while (count > 0 || eneCount > 0) {
            System.out.print("\nPlayer Turn: Enter an x and y value (ex. 1 1) ");
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            while (x < 0 || x > 9 || y < 0 || y > 9 || !enemyDisplay[x][y].equals(" ")) {
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
            for (int i = 0; i < 5; i++) {
                if (enemyExist[i]) {
                    eneCount++;
                }
            } if (eneCount == 0) {
                break;
            }
            x = (int)(Math.random() * 10);
            y = (int)(Math.random() * 10);
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
            System.out.println("Player win!");
        } else {
            System.out.println("Enemy win!");
        } in.close();
    }

    public boolean[] checkBoard(String[][] tempBoard, boolean[] tempLine) {
        boolean[] tempExist = {false, false, false, false, false};
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
                }
            }
        } for (int i = 0; i < 5; i++) {
            if (tempExist[i] != tempLine[i]) {
                System.out.println(shipNames[i] + " has been destroyed!");
            }
        }
        return tempExist;
    }

    public boolean turn(String[][] tempBoard, int x, int y) {
        if (!tempBoard[x][y].equals(" ")) {
            tempBoard[x][y] = "X";
            System.out.println("HIT");
            return true;
        } else {
            tempBoard[x][y] = "/";
            System.out.println("MISS");
            return false;
        }
    }


    public void printBoard(String[][] tempBoard) {
        System.out.println("    1 2 3 4 5 6 7 8 9 10 X");
        for (int i = 0; i < tempBoard[0].length; i++) {
            if (i < 9) {
                System.out.print(i + 1 + "   ");
            } else {
                System.out.print(i + 1 + "  ");
            }
            for (int j = 0; j < tempBoard.length; j++) {
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
            boolean placed = false;
            while (!placed) {
                int x = (int)(Math.random() * 10);
                int y = (int)(Math.random() * 10);
                boolean exists = false;
                if (x < tempBoard.length - counts[i]) {
                    for (int j = x; j < x + counts[i]; j++) {
                        if (tempBoard[j][y].equals(" ") == false) {
                            exists = true;
                        }
                    } if (exists == false) {
                        for (int k = x; k < x + counts[i]; k++) {
                            tempBoard[k][y] = ships[i];
                        }
                        placed = true;
                    }
                } else if (y < tempBoard[0].length - counts[i]) {
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
