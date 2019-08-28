import java.util.Scanner;

public class Main {
    private static int parisEnergy;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        parisEnergy = Integer.parseInt(sc.nextLine());
        int fieldRows = Integer.parseInt(sc.nextLine());

        String[][] field = new String[fieldRows][];
        for (int i = 0; i < field.length; i++) {
            field[i] = sc.nextLine().split("");
        }

        int[] parisLocation = locateParis(field);
        int parisRow = parisLocation[0];
        int parisCol = parisLocation[1];

        boolean isHelenAbducted = false;
        while (parisEnergy > 0 && !isHelenAbducted) {
            String[] tokens = sc.nextLine().split("\\s+");

            int enemyRow = Integer.parseInt(tokens[1]);
            int enemyCol = Integer.parseInt(tokens[2]);
            field[enemyRow][enemyCol] = "S";

            switch (tokens[0].toLowerCase()) {
                case "up":
                    parisEnergy--;

                    if (isInRange(field, parisRow - 1, parisCol)) {
                        field[parisRow][parisCol] = "-";
                        isHelenAbducted = isAbducted(field, --parisRow, parisCol);
                    }

                    if (isHelenAbducted) {
                        printHelenaOutput(parisEnergy, sb);
                    }

                    if (parisEnergy <= 0) {
                        printParisDeathOutput(field, sb);
                    }

                    break;
                case "down":
                    parisEnergy--;

                    if (isInRange(field, parisRow + 1, parisCol)) {
                        field[parisRow][parisCol] = "-";
                        isHelenAbducted = isAbducted(field, ++parisRow, parisCol);
                    }

                    if (isHelenAbducted) {
                        printHelenaOutput(parisEnergy, sb);
                    }

                    if (parisEnergy <= 0) {
                        printParisDeathOutput(field, sb);
                    }

                    break;
                case "left":
                    parisEnergy--;

                    if (isInRange(field, parisRow, parisCol - 1)) {
                        field[parisRow][parisCol] = "-";
                        isHelenAbducted = isAbducted(field, parisRow, --parisCol);
                    }

                    if (isHelenAbducted) {
                        printHelenaOutput(parisEnergy, sb);
                    }

                    if (parisEnergy <= 0) {
                        printParisDeathOutput(field, sb);
                    }

                    break;
                case "right":
                    parisEnergy--;

                    if (isInRange(field, parisRow, parisCol + 1)) {
                        field[parisRow][parisCol] = "-";
                        isHelenAbducted = isAbducted(field, parisRow, ++parisCol);
                    }

                    if (isHelenAbducted) {
                        printHelenaOutput(parisEnergy, sb);
                    }

                    if (parisEnergy <= 0) {
                        printParisDeathOutput(field, sb);
                    }

                    break;
            }
        }

        printField(field);
    }

    private static void printParisDeathOutput(String[][] field, StringBuilder sb) {
        int[] parisDeathLocation = locateParisDeath(field);
        sb.append(String.format("Paris died at %d;%d.",
                parisDeathLocation[0], parisDeathLocation[1]));
        System.out.println(sb.toString().trim());
    }

    private static void printHelenaOutput(int parisEnergy, StringBuilder sb) {
        sb.append("Paris has successfully abducted Helen! ")
                .append(String.format("Energy left: %d", parisEnergy));
        System.out.println(sb.toString().trim());
    }

    private static void printField(String[][] field) {
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

    private static int[] locateParisDeath(String[][] field) {
        int[] location = new int[2];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].equals("X")) {
                    location[0] = i;
                    location[1] = j;
                }
            }
        }

        return location;
    }

    private static int[] locateParis(String[][] field) {
        int[] location = new int[2];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].equals("P")) {
                    location[0] = i;
                    location[1] = j;
                }
            }
        }

        return location;
    }

    private static boolean isAbducted(String[][] field, int row, int col) {

        if (field[row][col].equals("S")) {
            parisEnergy -= 2;
            field[row][col] = "-";
        }
        if (field[row][col].equals("H")) {
            field[row][col] = "-";
            return true;
        }
        if (parisEnergy <= 0) {
            field[row][col] = "X";
        }

        return false;
    }

    private static boolean isInRange(String[][] field, int row, int col) {
        return row >= 0 && row < field.length && col >= 0 && col < field[row].length;
    }
}
