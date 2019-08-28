import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numberOfWaves = Integer.parseInt(sc.nextLine());
        ArrayDeque<Integer> plate =
                Arrays.stream(
                        sc.nextLine().split("\\s+"))
                        .filter(s -> s.matches("[0-9]+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayDeque::new));

        ArrayDeque<Integer> wave = Arrays.stream(
                sc.nextLine().split("\\s+"))
                .filter(s -> s.matches("[0-9]+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        int counter = 0;
        while (numberOfWaves-- > 0) {
            if (plate.isEmpty() || wave.isEmpty()) {
                break;
            }

            counter++;
            if (counter % 3 == 0) {
                int additionalPlate = Integer.parseInt(sc.nextLine());
                plate.addLast(additionalPlate);
            }

            int currentPlate;
            while (!wave.isEmpty() && !plate.isEmpty()) {
                currentPlate = plate.peekFirst();
                int currentWarrior;

                while (currentPlate > 0 && !wave.isEmpty()) {
                    currentWarrior = wave.removeLast();
                    currentPlate -= currentWarrior;
                    if (currentPlate < 0) {
                        String unsigned = String.valueOf(currentPlate).substring(1);
                        wave.addLast(Integer.parseInt(unsigned));
                    }
                }

                if (currentPlate > 0) {
                    plate.pop();
                    plate.push(currentPlate);
                } else {
                    plate.pop();
                }
            }

            if (numberOfWaves != 0 && !plate.isEmpty()) {
                wave = Arrays.stream(
                        sc.nextLine().split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayDeque::new));
            }
        }

        if (!plate.isEmpty()) {
            System.out.println("The Spartans successfully repulsed the Trojan attack.");
            System.out.println("Plates left: " + printWhatsLeft(plate));
        }
        if (!wave.isEmpty()) {
            System.out.println("The Trojans successfully destroyed the Spartan defense.");
            ArrayList<Integer> reversedCollection = new ArrayList<>(wave);
            Collections.reverse(reversedCollection);
            ArrayDeque<Integer> waveFinal = new ArrayDeque<>(reversedCollection);
            System.out.println("Warriors left: " + printWhatsLeft(waveFinal));
        }

    }

    private static String printWhatsLeft(ArrayDeque<Integer> deque) {
        StringBuilder sb = new StringBuilder();
        for (Integer integer : deque) {
            sb.append(integer).append(", ");
        }

        return sb.toString().trim().substring(0, sb.length() - 2);
    }
}
