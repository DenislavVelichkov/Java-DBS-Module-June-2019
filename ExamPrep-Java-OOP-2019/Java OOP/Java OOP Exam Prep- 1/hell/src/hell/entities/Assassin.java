package hell.entities;

public class Assassin extends Heroes {
    private static final int STRENGTH = 25;
    private static final int AGILITY = 100;
    private static final int INTELLIGENCE = 15;
    private static final int HITPOINTS = 150;
    private static final int DAMAGE = 300;

    public Assassin(String name) {
        super(name, STRENGTH, AGILITY, INTELLIGENCE, HITPOINTS, DAMAGE);
    }
}
