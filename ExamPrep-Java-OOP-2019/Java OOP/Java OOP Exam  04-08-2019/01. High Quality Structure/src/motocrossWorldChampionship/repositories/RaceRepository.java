package motocrossWorldChampionship.repositories;

import motocrossWorldChampionship.common.ExceptionMessages;
import motocrossWorldChampionship.entities.interfaces.Race;
import motocrossWorldChampionship.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RaceRepository implements Repository<Race> {
    private Collection<Race> data;

    public RaceRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public Race getByName(String name) {
        return this.data.stream().filter(race -> race.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public Collection<Race> getAll() {
        return Collections.unmodifiableCollection(this.data);
    }

    @Override
    public void add(Race race) {
        boolean isPresent = this.data.stream().anyMatch(race1 -> race1.getName().equals(race.getName()));
        if (isPresent) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, race.getName()));
        }

        this.data.add(race);
    }

    @Override
    public boolean remove(Race race) {
        return this.data.removeIf(race1 -> race1.getName().equals(race.getName()));
    }
}
