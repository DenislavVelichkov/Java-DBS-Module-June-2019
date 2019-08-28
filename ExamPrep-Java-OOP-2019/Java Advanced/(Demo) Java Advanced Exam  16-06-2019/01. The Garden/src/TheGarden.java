import java.util.Arrays;
import java.util.Scanner;

public class TheGarden {
    private static int carrots = 0;
    private static int potatoes = 0;
    private static int lettuce = 0;
    private static int harmedVegetables = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int rows = Integer.parseInt(sc.nextLine());
        String[][] garden = new String[rows][];
        for (int i = 0; i < garden.length; i++) {
            garden[i] = sc.nextLine().split("\\s+");
        }

        String command = sc.nextLine();
        while (!command.equalsIgnoreCase("End of Harvest")) {
            String[] tokens = command.split("\\s+");

            switch (tokens[0].toLowerCase()) {
                case "harvest":
                    int harvestRow = Integer.parseInt(tokens[1]);
                    int harvestCol = Integer.parseInt(tokens[2]);
                    harvestVegetable(garden, harvestRow, harvestCol);
                    break;
                case "mole":
                    int moleRow = Integer.parseInt(tokens[1]);
                    int moleCol = Integer.parseInt(tokens[2]);
                    String moleDirection = tokens[3];
                    moleMovement(garden, moleRow, moleCol, moleDirection);
                    break;
                default:
                    break;
            }

            command = sc.nextLine();
        }

        printGarden(garden);
        System.out.printf("Carrots: %d%nPotatoes: %d%nLettuce: %d%nHarmed vegetables: %d",
                carrots, potatoes, lettuce, harmedVegetables);
    }

    private static void moleMovement(String[][] garden, int moleRow, int moleCol, String moleDirection) {
        if (!isInRange(garden, moleRow, moleCol)) {
            return;
        }

        int movementRow;
        int movementCol;
        switch (moleDirection.toLowerCase()) {
            case "up":
                for (int i = moleRow; i >= 0; i-=2) {
                    movementRow = i;
                    if (isInRange(garden, movementRow, moleCol) &&
                            !garden[movementRow][moleCol].equals(" ")) {
                        harmedVegetables++;
                        garden[movementRow][moleCol] = " ";
                    }
                }

                break;
            case "left":
                for (int i = moleCol; i >= 0; i-=2) {
                    movementCol = i;
                    if (isInRange(garden, moleRow, movementCol) &&
                            !garden[moleRow][movementCol].equals(" ")) {
                        harmedVegetables++;
                        garden[moleRow][movementCol] = " ";
                    }
                }

                break;
            case "right":
                for (int i = moleCol; i < garden[moleRow].length; i+=2) {
                    movementCol = i;
                    if (isInRange(garden, moleRow, movementCol) &&
                            !garden[moleRow][movementCol].equals(" ")) {
                        harmedVegetables++;
                        garden[moleRow][movementCol] = " ";
                    }
                }

                break;
            case "down":
                for (int i = moleRow; i < garden.length; i+=2) {
                    movementRow = i;
                    if (isInRange(garden, movementRow, moleCol) &&
                            !garden[movementRow][moleCol].equals(" ")) {
                        harmedVegetables++;
                        garden[movementRow][moleCol] = " ";
                    }
                }

                break;
            default:
                break;
        }
    }

    private static void countHarvestedVegetables(String symbol) {
        switch (symbol.toUpperCase()) {
            case "P":
                potatoes += 1;
                break;
            case "L":
                lettuce += 1;
                break;
            case "C":
                carrots += 1;
                break;
            default:
                break;
        }
    }

    private static void harvestVegetable(String[][] garden, int row, int col) {
        if (isInRange(garden, row, col) && !garden[row][col].equals(" ")) {
            countHarvestedVegetables(garden[row][col]);
            garden[row][col] = " ";
        }
    }

    private static boolean isInRange(String[][] garden, int row, int col) {
        return row >= 0 && row < garden.length && col >= 0 && col < garden[row].length;
    }

    private static void printGarden(String[][] garden) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden[r].length; c++) {
                sb.append(garden[r][c]).append(" ");
            }

            String result = sb.substring(0, sb.length() - 1);
            System.out.println(result);
            sb = new StringBuilder();
        }
    }
}
