package hell.entities;

public class Wizard extends Heroes {
    private static final int STRENGTH = 25;
    private static final int AGILITY = 25;
    private static final int INTELLIGENCE = 100;
    private static final int HITPOINTS = 100;
    private static final int DAMAGE = 250;

    public Wizard(String name) {
        super(name, STRENGTH, AGILITY, INTELLIGENCE, HITPOINTS, DAMAGE);
    }
}
