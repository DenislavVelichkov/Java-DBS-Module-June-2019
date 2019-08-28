package JavaAdvanced.Exam24_02_19;

import java.util.Scanner;

public class TronRacers_2 {

    public static String[][] matrix;
    public static int playerOneRow;
    public static int playerTwoRow;
    public static int playerOneCol;
    public static int playerTwoCol;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());
        matrix = new String[n][];

        for (int i = 0; i < n; i++) {
            matrix[i] = sc.nextLine().split("");
        }

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col].charAt(0) == 'f') {
                    playerOneRow = row;
                    playerOneCol = col;
                }
                if (matrix[row][col].charAt(0) == 's') {
                    playerTwoRow = row;
                    playerTwoCol = col;
                }
            }
        }
        boolean isDead = false;

        while (!isDead) {
            String[] cmd = sc.nextLine().split(" ");
            String playerOneInstructions = cmd[0];
            String playerTwoInstructions = cmd[1];

            switch (playerOneInstructions) {
                case "up":
                    playerOneRow++;
                    if (ifInBounds(playerOneRow, playerOneCol)) {
                        playerOneRow = matrix.length - 1;

                        matrix[playerOneRow][playerOneCol] = "f";
                    } else {
                        if (matrix[playerOneRow][playerOneCol].equals(matrix[playerTwoRow][playerTwoCol])) {
                            matrix[playerOneRow][playerOneCol] = "x";
                        } else {
                            matrix[playerOneRow][playerOneCol] = "f";
                        }
                    }
                    break;
                case "down":
                    playerOneRow++;
                    if (ifInBounds(playerOneRow, playerOneCol)) {
                        playerOneRow = matrix.length - 1;

                        matrix[playerOneRow][playerOneCol] = "f";
                    } else {
                        if (matrix[playerOneRow][playerOneCol].equals(matrix[playerTwoRow][playerTwoCol])) {
                            matrix[playerOneRow][playerOneCol] = "x";
                        } else {
                            matrix[playerOneRow][playerOneCol] = "f";
                        }
                    }
                    break;
                case "left":
                    playerOneRow++;
                    if (ifInBounds(playerOneRow, playerOneCol)) {
                        playerOneRow = matrix.length - 1;

                        matrix[playerOneRow][playerOneCol] = "f";
                    } else {
                        if (matrix[playerOneRow][playerOneCol].equals(matrix[playerTwoRow][playerTwoCol])) {
                            matrix[playerOneRow][playerOneCol] = "x";
                        } else {
                            matrix[playerOneRow][playerOneCol] = "f";
                        }
                    }
                    break;
                case "right":
                    playerOneRow++;
                    if (ifInBounds(playerOneRow, playerOneCol)) {
                        playerOneRow = matrix.length - 1;

                        matrix[playerOneRow][playerOneCol] = "f";
                    } else {
                        if (matrix[playerOneRow][playerOneCol].equals(matrix[playerTwoRow][playerTwoCol])) {
                            matrix[playerOneRow][playerOneCol] = "x";
                        } else {
                            matrix[playerOneRow][playerOneCol] = "f";
                        }
                    }
                    break;
                default:
                    break;
            }

            switch (playerTwoInstructions) {
                case "up":
                    playerTwoRow++;
                    if (ifInBounds(playerTwoRow, playerTwoCol)) {
                        playerTwoRow = matrix.length - 1;
                        
                        matrix[playerTwoRow][playerTwoCol] = "s";
                    } else {
                        if (matrix[playerTwoRow][playerTwoCol].equals(matrix[playerOneRow][playerOneCol])) {
                            matrix[playerTwoRow][playerTwoCol] = "x";
                        } else {
                            matrix[playerTwoRow][playerTwoCol] = "s";
                        } 
                    }
                    break;
                case "down":
                    playerTwoRow--;
                    if (ifInBounds(playerTwoRow, playerTwoCol)) {
                        playerTwoRow = 0;
                        matrix[playerTwoRow][playerTwoCol] = "s";
                    } else {
                        if (matrix[playerTwoRow][playerTwoCol].equals(matrix[playerOneRow][playerOneCol])) {
                            matrix[playerTwoRow][playerTwoCol] = "x";
                        } else {
                            matrix[playerTwoRow][playerTwoCol] = "s";
                        }
                    }
                    break;
                case "left":
                    playerTwoCol--;
                    if (ifInBounds(playerTwoRow, playerTwoCol)) {
                        playerTwoCol = matrix.length - 1;
                        matrix[playerTwoRow][playerTwoCol] = "s";
                    } else {
                        if (matrix[playerTwoRow][playerTwoCol].equals(matrix[playerOneRow][playerOneCol])) {
                            matrix[playerTwoRow][playerTwoCol] = "x";
                        } else {
                            matrix[playerTwoRow][playerTwoCol] = "s";
                        }
                    }
                    break;
                case "right":
                    playerTwoCol++;
                    if (ifInBounds(playerTwoRow, playerTwoCol)) {
                        playerTwoCol = 0;
                        matrix[playerTwoRow][playerTwoCol] = "s";
                    } else {
                        if (matrix[playerTwoRow][playerTwoCol].equals(matrix[playerOneRow][playerOneCol])) {
                            matrix[playerTwoRow][playerTwoCol] = "x";
                        } else {
                            matrix[playerTwoRow][playerTwoCol] = "s";
                        }
                    }
                    break;
                default:
                    break;
            }
            isDead = true;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j].equals("f")) {
                        isDead = false;
                        break;
                    }
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j].equals("s")) {
                        isDead = false;
                        break;
                    }
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.print(matrix[i][j]);
                }
                System.out.println();
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    private static boolean ifInBounds(int row, int col) {
        return row >= 0 && row < matrix.length && col >= 0 && col < matrix.length;
    }
}