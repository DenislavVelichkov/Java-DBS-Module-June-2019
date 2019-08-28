package motocrossWorldChampionship.entities;

import motocrossWorldChampionship.common.ExceptionMessages;
import motocrossWorldChampionship.entities.interfaces.Motorcycle;

public abstract class MotorcycleImpl implements Motorcycle {
    private final static int MODEL_LENGTH = 4;
    private String model;
    private int horsePower;
    private double cubicCentimeters;

    protected MotorcycleImpl(String model, int horsePower, double cubicCentimeters) {
        this.setModel(model);
        this.setHorsePower(horsePower);
        this.cubicCentimeters = cubicCentimeters;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        if (model == null ||
            model.trim().isEmpty() ||
            model.length() < MODEL_LENGTH) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_MODEL,
                model, MODEL_LENGTH));
        }

        this.model = model;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    protected void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return cubicCentimeters / horsePower * laps;
    }
}
