import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<String> collectedSeashells = new LinkedList<>();
        List<String> stolenSeashells = new LinkedList<>();

        int sizeOfBeach = Integer.parseInt(sc.nextLine());
        String[][] theBeach = new String[sizeOfBeach][];
        for (int i = 0; i < theBeach.length; i++) {
            theBeach[i] = sc.nextLine().split("\\s+");

        }

        String command = sc.nextLine();
        while (!command.equals("Sunset")) {
            String[] tokens = command.split("\\s+");

            switch (tokens[0]) {
                case "Collect":
                    int collectRow = Integer.parseInt(tokens[1]);
                    int collectCol = Integer.parseInt(tokens[2]);

                    if (isInRange(theBeach, collectRow, collectCol)) {
                        String character = theBeach[collectRow][collectCol];
                        if (Character.isLetter(character.charAt(0))) {
                            collectedSeashells.add(character);
                            theBeach[collectRow][collectCol] = "-";
                        }
                    }


                    break;
                case "Steal":
                    int gullyRow = Integer.parseInt(tokens[1]);
                    int gullyCol = Integer.parseInt(tokens[2]);
                    String gullyDirection = tokens[3];

                    if (isInRange(theBeach, gullyRow, gullyCol)) {
                        int gullySteps = 3;
                        switch (gullyDirection.toLowerCase()) {
                            case "up":
                                while (gullySteps-- != 0) {
                                    if (Character.isLetter(theBeach[gullyRow][gullyCol].charAt(0))) {
                                        stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                        theBeach[gullyRow][gullyCol] = "-";
                                    }
                                    if (isInRange(theBeach, gullyRow - 1, gullyCol)) {
                                        if (Character.isLetter(theBeach[--gullyRow][gullyCol].charAt(0))) {
                                            stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                            theBeach[gullyRow][gullyCol] = "-";
                                        }
                                    }
                                }

                                break;
                            case "down":
                                while (gullySteps-- != 0) {
                                    if (Character.isLetter(theBeach[gullyRow][gullyCol].charAt(0))) {
                                        stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                        theBeach[gullyRow][gullyCol] = "-";
                                    }
                                    if (isInRange(theBeach, gullyRow + 1, gullyCol)) {
                                        if (Character.isLetter(theBeach[++gullyRow][gullyCol].charAt(0))) {
                                            stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                            theBeach[gullyRow][gullyCol] = "-";
                                        }
                                    }
                                }

                                break;
                            case "left":
                                while (gullySteps-- != 0) {
                                    if (Character.isLetter(theBeach[gullyRow][gullyCol].charAt(0))) {
                                        stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                        theBeach[gullyRow][gullyCol] = "-";
                                    }
                                    if (isInRange(theBeach, gullyRow, gullyCol - 1)) {
                                        if (Character.isLetter(theBeach[gullyRow][--gullyCol].charAt(0))) {
                                            stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                            theBeach[gullyRow][gullyCol] = "-";
                                        }
                                    }
                                }

                                break;
                            case "right":
                                while (gullySteps-- != 0) {
                                    if (Character.isLetter(theBeach[gullyRow][gullyCol].charAt(0))) {
                                        stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                        theBeach[gullyRow][gullyCol] = "-";
                                    }
                                    if (isInRange(theBeach, gullyRow, gullyCol + 1)) {
                                        if (Character.isLetter(theBeach[gullyRow][++gullyCol].charAt(0))) {
                                            stolenSeashells.add(theBeach[gullyRow][gullyCol]);
                                            theBeach[gullyRow][gullyCol] = "-";
                                        }
                                    }
                                }

                                break;
                        }
                    }

                    break;
            }

            command = sc.nextLine();
        }

        printTheBeach(theBeach);

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Collected seashells: %d -> ", collectedSeashells.size()));
        StringBuilder finalSb = sb;
        collectedSeashells.forEach(character -> {
            finalSb.append(character).append(", ");
        });

        if (collectedSeashells.size() != 0) {
            System.out.println(finalSb.toString().trim().substring(0, finalSb.length() - 2));
        } else {
            System.out.println(sb.toString().trim().substring(0, finalSb.length() - 3));
        }

        sb = new StringBuilder();
        sb.append(String.format("Stolen seashells: %d", stolenSeashells.size()));

        System.out.println(sb.toString().trim());
    }

    private static void printTheBeach(String[][] theBEach) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < theBEach.length; r++) {
            for (int c = 0; c < theBEach[r].length; c++) {
                sb.append(theBEach[r][c]).append(" ");
            }

            String result = sb.toString();
            System.out.println(result);
            sb = new StringBuilder();
        }
    }

    private static boolean isInRange(String[][] field, int row, int col) {
        return row >= 0 && row < field.length && col >= 0 && col < field[row].length;
    }

}
