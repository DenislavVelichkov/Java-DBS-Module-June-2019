import java.util.*;
import java.util.stream.Collectors;

public class Main {
    enum Cocktail {
        Mimosa(150),
        Daiquiri(250),
        Sunshine(300),
        Mojito(400);
        int value;

        Cocktail(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayDeque<Integer> ingrediants =
            Arrays.stream(sc.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .filter(integer -> integer != 0)
                .collect(Collectors.toCollection(ArrayDeque::new));

        ArrayDeque<Integer> freshnessValue =
            Arrays.stream(sc.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        Map<String, Integer> cocktailsMade = new HashMap<>();
        cocktailsMade.put("Mimosa", 0);
        cocktailsMade.put("Daiquiri", 0);
        cocktailsMade.put("Sunshine", 0);
        cocktailsMade.put("Mojito", 0);

        while (!ingrediants.isEmpty() && !freshnessValue.isEmpty()) {

            int result = ingrediants.peekFirst() * freshnessValue.removeLast();

            String cocktail = checkIfValueIsPresent(result);

            if (cocktail != null) {
                ingrediants.removeFirst();
                cocktailsMade.put(cocktail,
                    cocktailsMade.get(cocktail) + 1);
            } else {
                int addedValue = ingrediants.removeFirst() + 5;
                ingrediants.addLast(addedValue);
            }
        }

        if (checkRequiredCocktails(cocktailsMade)) {
            System.out.println("It's party time! The cocktails are ready!");
        } else {
            System.out.println("What a pity! You didn't manage to prepare all cocktails.");
        }

        StringBuilder sb = new StringBuilder();
        if (ingrediants.size() != 0) {
            System.out.println(String.format("Ingredients left: %d",
                ingrediants.stream().mapToLong(value -> value).sum()));
        }

        cocktailsMade.entrySet()
            .stream()
            .filter(entry -> entry.getValue() != 0)
            .sorted(Comparator.comparing(Map.Entry::getKey))
            .forEach(entry -> {
                sb.append(String.format("# %s --> %d", entry.getKey(), entry.getValue()))
                    .append(System.lineSeparator());
            });
        System.out.println(sb.toString());
    }

    private static boolean checkRequiredCocktails(Map<String, Integer> cocktailsMade) {
        boolean mimosaPresent =
            cocktailsMade.entrySet().stream()
                .anyMatch(entry ->
                    entry.getKey().equalsIgnoreCase(Cocktail.Mimosa.toString()) &&
                        entry.getValue() != 0);
        boolean daiquiriPresent =
            cocktailsMade.entrySet().stream()
                .anyMatch(entry ->
                    entry.getKey().equalsIgnoreCase(Cocktail.Daiquiri.toString()) &&
                        entry.getValue() != 0);
        boolean sunshinePresent =
            cocktailsMade.entrySet().stream()
                .anyMatch(entry ->
                    entry.getKey().equalsIgnoreCase(Cocktail.Sunshine.toString()) &&
                        entry.getValue() != 0);
        boolean mojitoPresent =
            cocktailsMade.entrySet().stream()
                .anyMatch(entry ->
                    entry.getKey().equalsIgnoreCase(Cocktail.Mojito.toString()) &&
                        entry.getValue() != 0);


        return mimosaPresent &&
            daiquiriPresent &&
            sunshinePresent &&
            mojitoPresent;

    }

    private static String checkIfValueIsPresent(int resultOfTheMix) {
        String result = null;

        if (Cocktail.Mimosa.getValue() == resultOfTheMix) {
            result = "Mimosa";
        } else if (Cocktail.Daiquiri.getValue() == resultOfTheMix) {
            result = "Daiquiri";
        } else if (Cocktail.Sunshine.getValue() == resultOfTheMix) {
            result = "Sunshine";
        } else if (Cocktail.Mojito.getValue() == resultOfTheMix) {
            result = "Mojito";
        }

        return result;
    }
}