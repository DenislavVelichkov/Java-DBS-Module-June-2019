package spaceStation.models.astronauts;

public class Biologist extends BaseAstronaut {
    private final static double INITIAL_OXYGEN = 70d;
    private final static double OXYGEN_CONSUMPTION = 5d;
    private final static double MIN_OXYGEN = 0d;

    public Biologist(String name) {
        super(name, INITIAL_OXYGEN);
    }

    @Override
    public void breath() {
        if (super.getOxygen() - OXYGEN_CONSUMPTION < MIN_OXYGEN) {
            super.setOxygen(MIN_OXYGEN);
        } else {
            super.setOxygen(super.getOxygen() - OXYGEN_CONSUMPTION);
        }
    }
}
