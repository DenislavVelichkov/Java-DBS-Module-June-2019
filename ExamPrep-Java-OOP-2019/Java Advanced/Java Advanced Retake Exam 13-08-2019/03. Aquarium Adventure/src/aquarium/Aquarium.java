package aquarium;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Aquarium {
    private String name;
    private int capacity;
    private int size;
    private Set<Fish> fishInPool;

    public Aquarium(String name, int capacity, int size) {
        this.name = name;
        this.capacity = capacity;
        this.size = size;
        this.fishInPool = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }


    public void setCapacity(int capacity) {
            this.capacity = capacity;

    }

    public int getFishInPool() {
        return fishInPool.size();
    }

    public void add(Fish fish) {
        boolean isFishPresent =
            this.fishInPool.stream()
                .anyMatch(fish1 -> fish1.getName().equals(fish.getName()));

        if (this.capacity > 0 && !isFishPresent) {
            this.fishInPool.add(fish);
            this.setCapacity(this.capacity - 1);
        }
    }

    public boolean remove(String name) {
        Fish fishToDelete = this.findFish(name);

        if (fishToDelete != null) {
            this.setCapacity(this.capacity + 1);
        }

        return this.fishInPool.remove(fishToDelete);
    }

    public Fish findFish(String name) {
        return this.fishInPool.stream()
                .filter(fish -> fish.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Aquarium: %s ^ Size: %d", this.name, this.size))
            .append(System.lineSeparator());
        this.fishInPool
            .stream()
            .sorted(Comparator.comparing(Fish::getFins))
            .forEach(fish -> sb.append(fish.toString()).append(System.lineSeparator()));

        return sb.toString();
    }
}
