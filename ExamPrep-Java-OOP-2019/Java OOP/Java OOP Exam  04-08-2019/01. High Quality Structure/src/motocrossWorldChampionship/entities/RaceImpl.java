package motocrossWorldChampionship.entities;

import motocrossWorldChampionship.common.ExceptionMessages;
import motocrossWorldChampionship.entities.interfaces.Race;
import motocrossWorldChampionship.entities.interfaces.Rider;

import java.util.ArrayList;
import java.util.Collection;

public class RaceImpl implements Race {
    private final static int MIN_RACE_NAME_LENGTH = 5;
    private final static int MIN_LAPS = 1;
    private String name;
    private int laps;
    private Collection<Rider> riders;

    public RaceImpl(String name, int laps) {
        this.setName(name);
        this.setLaps(laps);
        this.riders = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null ||
            name.trim().isEmpty() ||
            name.length() < MIN_RACE_NAME_LENGTH) {
            throw new IllegalArgumentException(
                String.format(ExceptionMessages.INVALID_NAME,
                    name, MIN_RACE_NAME_LENGTH));
        }

        this.name = name;
    }

    @Override
    public int getLaps() {
        return this.laps;
    }

    private void setLaps(int laps) {
        if (laps < MIN_LAPS) {
            throw new IllegalArgumentException(
                String.format(ExceptionMessages.INVALID_NUMBER_OF_LAPS, laps));
        }

        this.laps = laps;
    }

    @Override
    public Collection<Rider> getRiders() {
        return this.riders;
    }

    @Override
    public void addRider(Rider rider) {
        if (rider == null) {
            throw new NullPointerException (ExceptionMessages.RIDER_INVALID);
        }
        if (!rider.getCanParticipate()) {
            throw new IllegalArgumentException(
                String.format(ExceptionMessages.RIDER_NOT_PARTICIPATE, rider.getName()));
        }

        boolean isPresent = this.riders.stream().anyMatch(rider1 -> rider1.getName().equals(rider.getName()));
        if (isPresent) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RIDER_ALREADY_ADDED,
                rider.getName(), this.getName()));
        }

        this.riders.add(rider);
    }
}
