package JavaAdvanced.Exam24_02_19.Zad_3.heroRepository;

public class Hero {
    private String name;
    private int level;
    private Item item;

    public Hero(String name, int level, Item item) {
        this.name = name;
        this.level = level;
        this.item = item;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public Item getItem() {
        return this.item;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Hero: ").append(this.name).append(" - ").append(this.level).append("lvl")
                .append(System.lineSeparator())
        .append(this.getItem().toString());
                /*.append(" * Strength: ").append(this.getItem().getStrength()).append(ComputerSystem.lineSeparator())
                .append(" * Agility: ").append(this.getItem().getAgility()).append(ComputerSystem.lineSeparator())
                .append(" * Intelligence: ").append(this.getItem().getIntelligence()).append(ComputerSystem.lineSeparator());*/

        return str.toString();
    }
}
