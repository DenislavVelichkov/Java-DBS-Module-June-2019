import java.util.Scanner;

public class Main {
    private static int starPower = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int sizeOfGalaxy = Integer.parseInt(sc.nextLine());
        char[][] galaxy = new char[sizeOfGalaxy][];
        for (int i = 0; i < galaxy.length; i++) {
            galaxy[i] = sc.nextLine().toCharArray();

        }

        int[] starshipLocation = locateStarship(galaxy);
        int starshipRow = starshipLocation[0];
        int starshipCol = starshipLocation[1];
        int[] blackHoleLocation;

        boolean isLost = false;
        while (starPower < 50 && !isLost) {
            String tokens = sc.nextLine();

            boolean isInRange;
            switch (tokens.toLowerCase()) {
                case "up":
                     isInRange = isInRange(galaxy, starshipRow - 1, starshipCol);

                    if (isInRange && galaxy[starshipRow - 1][starshipCol] == '-') {
                        galaxy[starshipRow][starshipCol] = '-';
                        --starshipRow;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && galaxy[starshipRow - 1][starshipCol] == 'O') {
                        galaxy[starshipRow][starshipCol] = '-';
                        galaxy[--starshipRow][starshipCol] = '-';
                        blackHoleLocation = locateBlackHole(galaxy);
                        int blackHoleRow = blackHoleLocation[0];
                        int blackHoleCol = blackHoleLocation[1];
                        starshipRow = blackHoleRow;
                        starshipCol = blackHoleCol;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && Character.isDigit(galaxy[starshipRow - 1][starshipCol])) {
                        galaxy[starshipRow][starshipCol] = '-';
                        starPower += Character.getNumericValue(galaxy[--starshipRow][starshipCol]);
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (!isInRange) {
                        galaxy[starshipRow][starshipCol] = '-';
                        System.out.println("Bad news, the spaceship went to the void.");
                        isLost = true;
                    }

                    break;
                case "down":
                    isInRange = isInRange(galaxy, starshipRow + 1, starshipCol);

                    if (isInRange && galaxy[starshipRow + 1][starshipCol] == '-') {
                        galaxy[starshipRow][starshipCol] = '-';
                        ++starshipRow;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && galaxy[starshipRow + 1][starshipCol] == 'O') {
                        galaxy[starshipRow][starshipCol] = '-';
                        galaxy[++starshipRow][starshipCol] = '-';
                        blackHoleLocation = locateBlackHole(galaxy);
                        int blackHoleRow = blackHoleLocation[0];
                        int blackHoleCol = blackHoleLocation[1];
                        starshipRow = blackHoleRow;
                        starshipCol = blackHoleCol;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && Character.isDigit(galaxy[starshipRow + 1][starshipCol])) {
                        galaxy[starshipRow][starshipCol] = '-';
                        starPower += Character.getNumericValue(galaxy[++starshipRow][starshipCol]);
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (!isInRange) {
                        galaxy[starshipRow][starshipCol] = '-';
                        System.out.println("Bad news, the spaceship went to the void.");
                        isLost = true;
                    }

                    break;
                case "left":
                    isInRange = isInRange(galaxy, starshipRow, starshipCol - 1);

                    if (isInRange && galaxy[starshipRow][starshipCol - 1] == '-') {
                        galaxy[starshipRow][starshipCol] = '-';
                        --starshipCol;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && galaxy[starshipRow][starshipCol - 1] == 'O') {
                        galaxy[starshipRow][starshipCol] = '-';
                        galaxy[starshipRow][--starshipCol] = '-';
                        blackHoleLocation = locateBlackHole(galaxy);
                        int blackHoleRow = blackHoleLocation[0];
                        int blackHoleCol = blackHoleLocation[1];
                        starshipRow = blackHoleRow;
                        starshipCol = blackHoleCol;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && Character.isDigit(galaxy[starshipRow][starshipCol - 1])) {
                        galaxy[starshipRow][starshipCol] = '-';
                        starPower += Character.getNumericValue(galaxy[starshipRow][--starshipCol]);
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (!isInRange) {
                        galaxy[starshipRow][starshipCol] = '-';
                        System.out.println("Bad news, the spaceship went to the void.");
                        isLost = true;
                    }

                    break;
                case "right":
                    isInRange = isInRange(galaxy, starshipRow, starshipCol + 1);

                    if (isInRange && galaxy[starshipRow][starshipCol + 1] == '-') {
                        galaxy[starshipRow][starshipCol] = '-';
                        ++starshipCol;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && galaxy[starshipRow][starshipCol + 1] == 'O') {
                        galaxy[starshipRow][starshipCol] = '-';
                        galaxy[starshipRow][++starshipCol] = '-';
                        blackHoleLocation = locateBlackHole(galaxy);
                        int blackHoleRow = blackHoleLocation[0];
                        int blackHoleCol = blackHoleLocation[1];
                        starshipRow = blackHoleRow;
                        starshipCol = blackHoleCol;
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (isInRange && Character.isDigit(galaxy[starshipRow][starshipCol + 1])) {
                        galaxy[starshipRow][starshipCol] = '-';
                        starPower += Character.getNumericValue(galaxy[starshipRow][++starshipCol]);
                        galaxy[starshipRow][starshipCol] = 'S';
                    } else if (!isInRange) {
                        galaxy[starshipRow][starshipCol] = '-';
                        System.out.println("Bad news, the spaceship went to the void.");
                        isLost = true;
                    }

                    break;
            }
        }

        if (starPower >= 50) {
            System.out.println("Good news! Stephen succeeded in collecting enough star power!");
        }

        System.out.println("Star power collected: " + starPower);
        printField(galaxy);
    }

    private static int[] locateBlackHole(char[][] galaxy) {
        int[] location = new int[2];
        for (int i = 0; i < galaxy.length; i++) {
            for (int j = 0; j < galaxy[i].length; j++) {
                if (galaxy[i][j] == 'O') {
                    location[0] = i;
                    location[1] = j;
                }
            }
        }

        return location;
    }

    private static int[] locateStarship(char[][] galaxy) {
        int[] location = new int[2];
        for (int i = 0; i < galaxy.length; i++) {
            for (int j = 0; j < galaxy[i].length; j++) {
                if (galaxy[i][j] == 'S') {
                    location[0] = i;
                    location[1] = j;
                }
            }
        }

        return location;
    }

    private static void printField(char[][] field) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < field.length; r++) {
            for (int c = 0; c < field[r].length; c++) {
                sb.append(field[r][c]);
            }

            String result = sb.toString();
            System.out.println(result);
            sb = new StringBuilder();
        }
    }

    private static boolean isInRange(char[][] field, int row, int col) {
        return row >= 0 && row < field.length && col >= 0 && col < field[row].length;
    }
}
