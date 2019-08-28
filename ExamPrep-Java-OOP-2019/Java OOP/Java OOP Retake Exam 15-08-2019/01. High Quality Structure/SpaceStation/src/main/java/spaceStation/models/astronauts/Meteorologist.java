package spaceStation.models.astronauts;

public class Meteorologist extends BaseAstronaut{
    private final static double INITIAL_OXYGEN = 90d;

    public Meteorologist(String name) {
        super(name, INITIAL_OXYGEN);
    }
}
