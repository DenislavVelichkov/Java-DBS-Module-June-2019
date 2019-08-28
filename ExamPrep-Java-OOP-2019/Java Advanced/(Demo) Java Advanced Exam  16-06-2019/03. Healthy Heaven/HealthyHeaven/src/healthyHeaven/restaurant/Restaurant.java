package healthyHeaven.restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Restaurant {
    private String name;
    private List<Salad> data;

    public Restaurant(String name) {
        this.data = new ArrayList<>();
        this.name = name;
    }

    public void add(Salad salad) {
        boolean saladIsPresent = this.data.stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase(salad.getName()));
        if (!saladIsPresent) {
            this.data.add(salad);
        }

    }

    public boolean buy(String name) {
        Salad saladToRemove =
                this.data.stream()
                        .filter(salad -> salad.getName().equalsIgnoreCase(name))
                        .findFirst()
                        .orElse(null);

        if (saladToRemove != null) {
            return this.data.remove(saladToRemove);
        }

        return false;
    }

    public Salad getHealthiestSalad() {
        return this.data.stream()
                .min(Comparator.comparing(Salad::getTotalCalories))
                .orElse(null);
    }

    public String generateMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s have %d salads:", this.name, this.data.size()))
                .append(System.lineSeparator());
        this.data
                .forEach(salad -> sb.append(salad.toString()).append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
