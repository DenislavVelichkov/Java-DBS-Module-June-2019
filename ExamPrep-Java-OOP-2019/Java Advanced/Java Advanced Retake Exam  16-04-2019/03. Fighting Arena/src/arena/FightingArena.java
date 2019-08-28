package arena;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FightingArena {
    private String name;
    private List<Gladiator> gladiators;

    public FightingArena(String name) {
        this.name = name;
        this.gladiators = new ArrayList<>();
    }

    public void add(Gladiator gladiator) {
        boolean isPresent = this.gladiators
                .stream()
                .anyMatch(gl -> gl.getName().equalsIgnoreCase(gladiator.getName()));

        if (!isPresent) {
            this.gladiators.add(gladiator);
        }
    }

    public void remove(String name) {
        Gladiator gladiatorToRemove =
                this.gladiators.stream()
                        .filter(gladiator -> gladiator.getName().equalsIgnoreCase(name))
                        .findAny()
                        .orElse(null);

        this.gladiators.remove(gladiatorToRemove);
    }

    public Gladiator getGladiatorWithHighestStatPower() {
        return this.gladiators.stream()
                .max(Comparator.comparingInt(Gladiator::getStatPower))
                .get();
    }

    public Gladiator getGladiatorWithHighestWeaponPower() {
        return this.gladiators.stream()
                .max(Comparator.comparingInt(Gladiator::getWeaponPower))
                .get();
    }

    public Gladiator getGladiatorWithHighestTotalPower() {
        return this.gladiators.stream()
                .max(Comparator.comparingInt(Gladiator::getTotalPower))
                .get();
    }

    public int getCount() {
        return this.gladiators.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s – %d gladiators are participating.",
                this.name, this.getCount()))
        .append(System.lineSeparator());

        return sb.toString().trim();
    }
}
