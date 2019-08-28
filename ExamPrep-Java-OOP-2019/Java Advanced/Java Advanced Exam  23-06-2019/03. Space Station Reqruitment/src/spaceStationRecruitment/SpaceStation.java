package spaceStationRecruitment;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SpaceStation {
    private String name;
    private int capacity;
    private List<Astronaut> data;

    public SpaceStation(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.data = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCount() {
        return this.data.size();
    }

    public void add(Astronaut astronaut) {
        boolean isAstronautPresent =
            this.data.stream()
                .anyMatch(astronaut1 -> astronaut1.getName().equalsIgnoreCase(astronaut.getName()));

        if (!isAstronautPresent && capacity != 0) {
            this.data.add(astronaut);
            capacity--;
        }
    }

    public boolean remove(String name) {
        Astronaut astronaut =
            this.data.stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
        if (astronaut != null) {
            capacity++;
        }

        return this.data.remove(astronaut);
    }

    public Astronaut getOldestAstronaut() {
        return this.data.stream().max(Comparator.comparingInt(Astronaut::getAge)).get();
    }

    public Astronaut getAstronaut(String name) {
        return this.data.stream()
            .filter(astronaut -> astronaut.getName().equalsIgnoreCase(name))
            .findAny()
            .orElse(null);
    }

    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Astronauts working at Space Station %s:", name))
            .append(System.lineSeparator());
        this.data.forEach(astronaut -> sb.append(astronaut.toString()).append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
