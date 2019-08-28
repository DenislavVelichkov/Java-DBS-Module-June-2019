package hell;

import hell.entities.Assassin;
import hell.entities.Barbarian;
import hell.entities.Wizard;
import hell.entities.items.CommonItem;
import hell.entities.items.RecipeItem;
import hell.interfaces.Hero;
import hell.interfaces.Item;
import hell.interfaces.Manager;
import hell.interfaces.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine implements Runnable, Manager {
    private Scanner scanner;
    private List<Hero> heroes = new ArrayList<>();

    public Engine() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        String input = scanner.nextLine();

        while (!input.equals("Quit")) {
            String[] tokens = input.split("\\s+");

            switch (tokens[0]) {
                case "Hero":
                    System.out.println(addHero(Arrays.asList(tokens)));
                    break;
                case "Item":
                    System.out.println(addItem(Arrays.asList(tokens)));
                    break;
                case "Recipe":
                    System.out.println(addRecipe(Arrays.asList(tokens)));
                    break;
                case "Inspect":
                    System.out.println(inspect(Arrays.asList(tokens)));
                    break;
            }

            input = scanner.nextLine();
        }

        try {
            System.out.println(quit());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String addHero(List<String> arguments) {
        Hero hero = null;

        switch (arguments.get(2)) {
            case "Barbarian":
                hero = new Barbarian(arguments.get(1));
                break;
            case "Assassin":
                hero = new Assassin(arguments.get(1));
                break;
            case "Wizard":
                hero = new Wizard(arguments.get(1));
                break;
        }

        this.heroes.add(hero);
        return "Created " +
                   hero.getClass().getSimpleName() +
                   " - " +
                   hero.getName();
    }

    @Override
    public String addItem(List<String> arguments) {
        String heroWhoGotItem = "";

        Item item = new CommonItem(
            arguments.get(1),
            Integer.parseInt(arguments.get(3)),
            Integer.parseInt(arguments.get(4)),
            Integer.parseInt(arguments.get(5)),
            Integer.parseInt(arguments.get(6)),
            Integer.parseInt(arguments.get(7))
        );

        for (Hero hero : heroes) {
            if (hero.getName().equals(arguments.get(2))) {
                hero.addItem(item);
                heroWhoGotItem = hero.getName();
                break;
            }
        }

        return "Added item" +
                   " - " +
                   item.getName() +
                   " to Hero - " +
                   heroWhoGotItem;
    }

    @Override
    public String addRecipe(List<String> arguments) {
        String[] requiredItems = arguments.subList(8, arguments.size()).toArray(new String[0]);
        String heroWhoGotItem = "";

        Recipe recipeItem = new RecipeItem(
            arguments.get(1),
            Integer.parseInt(arguments.get(3)),
            Integer.parseInt(arguments.get(4)),
            Integer.parseInt(arguments.get(5)),
            Integer.parseInt(arguments.get(6)),
            Integer.parseInt(arguments.get(7)),
            requiredItems
        );

        for (Hero hero : heroes) {
            if (hero.getName().equals(arguments.get(2))) {
                hero.addRecipe(recipeItem);
                heroWhoGotItem = hero.getName();
                break;
            }
        }

        return "Added recipe" +
                   " - " +
                   recipeItem.getName() +
                   " to Hero - " +
                   heroWhoGotItem;
    }

    @Override
    public String inspect(List<String> arguments) {
        String heroToInspectName = arguments.get(1);
        Hero heroToInspect = null;

        for (Hero hero : this.heroes) {
            if (hero.getName().equals(heroToInspectName)) {
                heroToInspect = hero;
                break;
            }
        }

        return heroToInspect.toString();
    }

    @Override
    public String quit() throws NoSuchFieldException, IllegalAccessException {

        List<Hero> sortedHeroes = this.heroes.stream().sorted((o1, o2) -> {
            long sortCriteriaOne = o1.getAgility() + o1.getStrength() + o1.getIntelligence();
            long sortCriteriaTwo = o2.getAgility() + o2.getStrength() + o2.getIntelligence();

            long result = sortCriteriaTwo - sortCriteriaOne;
            return result != 0 ?
                       (int) result :
                       Long.compare(
                           (o2.getDamage() + o2.getHitPoints()),
                           (o1.getDamage() + o1.getHitPoints())
                       );
        }).collect(Collectors.toList());

        int counter = 1;
        String finalString = "";
        StringBuilder sb = new StringBuilder();

        for (Hero hero : sortedHeroes) {
            sb.append(counter++)
                .append(". ")
                .append(hero.getClass().getSimpleName())
                .append(": ")
                .append(hero.getName())
                .append(System.lineSeparator())
                .append("###HitPoints: ")
                .append(hero.getHitPoints())
                .append(System.lineSeparator())
                .append("###Damage: ")
                .append(hero.getDamage())
                .append(System.lineSeparator())
                .append("###Strength: ")
                .append(hero.getStrength())
                .append(System.lineSeparator())
                .append("###Agility: ")
                .append(hero.getAgility())
                .append(System.lineSeparator())
                .append("###Intelligence: ")
                .append(hero.getIntelligence())
                .append(System.lineSeparator())
                .append("###Items: ");

            if (hero.getItems().isEmpty()) {
                sb.append("None");
                sb.append(System.lineSeparator());
                finalString += sb.toString();

            } else {
                for (Item item : hero.getItems()) {
                    sb.append(item.getName()).append(", ");
                }

                finalString += sb.toString().trim().substring(0, sb.lastIndexOf(",")) + System.lineSeparator();
            }

            sb = new StringBuilder();
        }

        return finalString;
    }
}
