package JavaAdvanced.Exam24_02_19.Zad_3.heroRepository;

public class Item {
    private int strength;
    private int intelligence;
    private int agility;

    public Item() {

    }
    public Item(int strength, int intelligence, int agility) {
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getAgility() {
        return this.agility;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Item:").append(System.lineSeparator())
                .append(" * Strength: ").append(this.strength).append(System.lineSeparator())
                .append(" * Agility: ").append(this.agility).append(System.lineSeparator())
                .append(" * Intelligence: ").append(this.intelligence).append(System.lineSeparator());

        return str.toString();
    }
}
