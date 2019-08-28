package JavaAdvanced.Exam24_02_19;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClubParty_1VER2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int hallCapacity = Integer.parseInt(sc.nextLine());
        String[] hallsAndPeople = sc.nextLine().split(" ");

        ArrayDeque<String> halls = new ArrayDeque<>();
        List<Integer> currentHallReservations = new ArrayList<>();

        for (int i = hallsAndPeople.length - 1; i >= 0; i--) {
            String current = hallsAndPeople[i];

            if (Character.isDigit(current.charAt(0))) {
                if (halls.isEmpty()) { continue; }

                int currentReservation = Integer.parseInt(current);

                if (currentReservation > hallCapacity) { continue; }

                int sum = currentHallReservations.stream().mapToInt(x -> x).sum();
                if (sum + currentReservation <= hallCapacity) {
                    currentHallReservations.add(currentReservation);
                } else {
                    printHall(halls.poll(), currentHallReservations);
                    currentHallReservations.clear();
                    i++;
                }
            } else {
                halls.offer(current);
            }
        }
    }

    private static void printHall(String poll, List<Integer> currentHallReservations) {
        System.out.print(poll);
        System.out.print(" -> ");
        System.out.println(currentHallReservations
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }
}