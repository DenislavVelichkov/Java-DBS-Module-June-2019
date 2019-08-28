package healthyHeaven.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Salad {
    private String name;
    private List<Vegetable> products;

    public Salad(String name) {
        this.products = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getTotalCalories() {
        return (int) this.products.stream().mapToLong(Vegetable::getCalories).sum();
    }

    public int getProductCount() {
        return this.products.size();
    }

    public void add(Vegetable product) {
        boolean vegetableIsPresent =
                this.products
                        .stream()
                        .anyMatch(vegetable -> vegetable.getName().equalsIgnoreCase(product.getName()));

        if (product.getCalories() > 0 &&
                !vegetableIsPresent) {
            this.products.add(product);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("* Salad %s is %d calories and have %d products:",
                this.name, this.getTotalCalories(), this.getProductCount()))
                .append(System.lineSeparator());
        this.products
                .forEach(vegetable -> sb.append(vegetable.toString())
                        .append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
