import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

class MakeASalad {
    enum Calories {
        tomato(80),
        carrot(136),
        lettuce(109),
        potato(215);

        int value;

        Calories(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayDeque<String> vegetables = Arrays.stream(
                sc.nextLine().split("\\s+"))
                .filter(s -> s.matches("tomato|carrot|lettuce|potato"))
                .collect(Collectors.toCollection(ArrayDeque::new));
        ArrayDeque<Integer> saladCalories = Arrays.stream(
                sc.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        StringBuilder sb = new StringBuilder();
        while (saladCalories.size() != 0 && vegetables.size() != 0) {
            int caloriesNeeded = saladCalories.peekLast();

            while (caloriesNeeded > 0 && vegetables.size() != 0) {
                caloriesNeeded -= Calories.valueOf(vegetables.pop()).getValue();
            }

            sb.append(saladCalories.removeLast()).append(" ");
        }

        System.out.println(sb.toString().trim());

        sb = new StringBuilder();
        if (vegetables.size() != 0) {
            for (String vegetable : vegetables) {
                sb.append(vegetable).append(" ");
            }

            System.out.println(sb.toString().trim());
        }
        if (saladCalories.size() != 0) {
            while (!saladCalories.isEmpty()) {
                sb.append(saladCalories.removeLast()).append(" ");
            }

            System.out.println(sb.toString().trim());
        }
    }
}
