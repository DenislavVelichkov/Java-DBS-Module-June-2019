package JavaAdvanced.Exam24_02_19;

import java.util.*;

public class ClubParty_1 {
    private static String hallToAdd;
    private static StringBuilder finalStr;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int maxCapacity = Integer.parseInt(sc.nextLine());

        String[] line = sc.nextLine().split(" ");
        ArrayDeque<String> data = new ArrayDeque<>(Arrays.asList(line));
        ArrayList<String> tempListForHalls = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        HashMap<String, List<Integer>> reservations = new LinkedHashMap<>();
        int hallCapacity = 0;

        while (!data.isEmpty()) {

            if (Character.isDigit(data.peekLast().charAt(0))) {

                for (int i = 0; i < tempListForHalls.size(); i++) {
                    hallCapacity = reservations.get(tempListForHalls.get(i)).stream().mapToInt(value -> value).sum();

                    if (hallCapacity <= maxCapacity) {
                        reservations.get(tempListForHalls.get(i)).add(Integer.parseInt(data.peekLast()));
                        finalStr = new StringBuilder();

                        reservations.get(tempListForHalls.get(i))
                                .stream()
                                .forEach(integer -> finalStr.append(integer).append(", "));

                        String trimStr = finalStr.toString().substring(0, str.length() - 2);

                        str = new StringBuilder();
                        str.append(tempListForHalls.get(i)).append(" -> ").append(trimStr).append(System.lineSeparator());
                        break;
                    }
                }

                data.pollLast();
            } else {
                if (hallCapacity < maxCapacity) {
                    hallToAdd = data.pollLast();
                    tempListForHalls.add(hallToAdd);
                    reservations.putIfAbsent(hallToAdd, new ArrayList<>());
                }
            }
        }

        System.out.println(str);
    }
}
