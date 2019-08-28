package motocrossWorldChampionship.repositories;

import motocrossWorldChampionship.common.ExceptionMessages;
import motocrossWorldChampionship.entities.interfaces.Motorcycle;
import motocrossWorldChampionship.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MotorcycleRepository implements Repository<Motorcycle> {
    private Collection<Motorcycle> data;

    public MotorcycleRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public Motorcycle getByName(String model) {
        return this.data.stream().filter(motorcycle -> motorcycle.getModel().equals(model)).findAny().orElse(null);
    }

    @Override
    public Collection<Motorcycle> getAll() {
        return Collections.unmodifiableCollection(this.data);
    }

    @Override
    public void add(Motorcycle model) {
        boolean isPresent = this.data.stream().anyMatch(motorcycle -> motorcycle.getModel().equals(model.getModel()));
        if (isPresent) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.MOTORCYCLE_EXISTS, model.getModel()));
        }

        this.data.add(model);
    }

    @Override
    public boolean remove(Motorcycle motorcycle) {
        return this.data.removeIf(motorcycle1 -> motorcycle1.getModel().equals(motorcycle.getModel()));
    }
}
