package JavaAdvanced.ExamPrep_24;

import java.util.Scanner;

public class Sneaking_2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        char[][] room = new char[n][];
        int[] samPos = new int[2];
        int[] nikolaPos = new int[2];

        for (int row = 0; row < n; row++) {
            String line = sc.nextLine();
            room[row] = line.toCharArray();

            if (line.contains("N")) {
                nikolaPos[0] = row;
                nikolaPos[1] = line.indexOf("N");
            } else if (line.contains("S")) {
                samPos[0] = row;
                samPos[1] = line.indexOf("S");
            }

        }

        String command = sc.nextLine();

        for (int i = 0; i < command.length(); i++) {
            moveEnemies(room);

            boolean samIsDead = isSamDead(room, samPos);

            if (samIsDead) {
                room[samPos[0]][samPos[1]] = 'X';
                System.out.printf("Sam died at %d, %d%n", samPos[0], samPos[1]);
                break;
            } else {
                moveSam(room, samPos, command.charAt(i));
            }

            if (nikolaPos[0] == samPos[0]) {
                System.out.println("Nikoladze killed!");
                room[nikolaPos[0]][nikolaPos[1]] = 'X';
                break;
            }
        }

        for (int row = 0; row < room.length; row++) {
            for (int col = 0; col < room[row].length; col++) {
                System.out.print(room[row][col]);
            }
            System.out.println();
        }
    }

    private static void moveSam(char[][] room, int[] samPos, char direction) {
        if (direction == 'U') {
            room[samPos[0]--][samPos[1]] = '.';
            room[samPos[0]][samPos[1]] = 'S';
        } else if (direction == 'D') {
            room[samPos[0]++][samPos[1]] = '.';
            room[samPos[0]][samPos[1]] = 'S';
        } else if (direction == 'L') {
            room[samPos[0]][samPos[1]--] = '.';
            room[samPos[0]][samPos[1]] = 'S';
        } else if (direction == 'R') {
            room[samPos[0]][samPos[1]++] = '.';
            room[samPos[0]][samPos[1]] = 'S';
        }
    }

    private static boolean isSamDead(char[][] room, int[] samPos) {
        for (int col = 0; col < samPos[1]; col++) {
            if (room[samPos[0]][col] == 'b') {
                return true;
            }
        }

        for (int col = samPos[1] + 1; col < room[samPos[0]].length; col++) {
            if (room[samPos[0]][col] == 'd') {
                return true;
            }
        }
        return false;
    }

    private static void moveEnemies(char[][] room) {
        for (int row = 0; row < room.length; row++) {
            for (int col = 0; col < room[row].length; col++) {
                if (room[row][col] == 'b') {
                    if (col == room[row].length - 1) {
                        room[row][col] = 'd';
                    } else {
                        room[row][col] = '.';
                        room[row][col + 1] = 'b';
                        break;
                    }
                } else if (room[row][col] == 'd') {
                    if (col == 0) {
                        room[row][col] = 'b';
                    } else {
                        room[row][col] = '.';
                        room[row][col - 1] = 'd';
                        break;
                    }
                }
            }
        }
    }
}