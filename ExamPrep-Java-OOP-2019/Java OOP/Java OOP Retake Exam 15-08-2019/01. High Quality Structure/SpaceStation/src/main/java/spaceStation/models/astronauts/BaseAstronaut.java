package spaceStation.models.astronauts;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.bags.Backpack;
import spaceStation.models.bags.Bag;

public abstract class BaseAstronaut implements Astronaut {
    private final static double MIN_OXYGEN = 0d;
    private final static double OXYGEN_CONSUMPTION = 10d;

    private String name;
    private double oxygen;
    private Bag bag;

  protected BaseAstronaut(String name, double oxygen) {
      this.bag = new Backpack();
      this.setName(name);
      this.setOxygen(oxygen);
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.ASTRONAUT_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    @Override
    public double getOxygen() {
        return this.oxygen;
    }

    void setOxygen(double oxygen) {
        if (oxygen < MIN_OXYGEN) {
            throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_OXYGEN_LESS_THAN_ZERO);
        }

        this.oxygen = oxygen;
    }

    @Override
    public boolean canBreath() {
        return !(this.getOxygen() > MIN_OXYGEN);
    }

    @Override
    public Bag getBag() {
        return this.bag;
    }

    @Override
    public void breath() {
        if ((this.getOxygen() - OXYGEN_CONSUMPTION) < MIN_OXYGEN) {
            this.setOxygen(MIN_OXYGEN);
        } else {
            this.setOxygen(this.getOxygen() - OXYGEN_CONSUMPTION);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String finalString;

        sb.append(String.format(
                ConstantMessages.REPORT_ASTRONAUT_NAME, this.name))
                .append(System.lineSeparator())
            .append(String.format(
                    ConstantMessages.REPORT_ASTRONAUT_OXYGEN, this.oxygen))
                .append(System.lineSeparator())
            .append(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS);

        if (this.bag.getItems().isEmpty()) {
            sb.append("none");
            finalString = sb.toString().trim();
        } else {
            this.bag.getItems()
                    .forEach(item -> sb.append(item)
                    .append(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER));
            finalString = sb.toString().trim().substring(0, sb.length() - 2);
        }

        return finalString;
    }
}
