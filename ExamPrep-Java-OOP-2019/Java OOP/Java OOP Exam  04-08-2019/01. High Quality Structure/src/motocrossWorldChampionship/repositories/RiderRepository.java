package motocrossWorldChampionship.repositories;

import motocrossWorldChampionship.common.ExceptionMessages;
import motocrossWorldChampionship.entities.interfaces.Rider;
import motocrossWorldChampionship.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RiderRepository implements Repository<Rider> {
    private Collection<Rider> data;

    public RiderRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public Rider getByName(String name) {
        return this.data.stream().filter(rider -> rider.getName().equals(name)).findAny().orElse(null);

    }

    @Override
    public Collection<Rider> getAll() {
        return Collections.unmodifiableCollection(this.data);
    }

    @Override
    public void add(Rider rider) {
        boolean isPresent = this.data.stream().anyMatch(rider1 -> rider1.getName().equals(rider.getName()));
        if (isPresent) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RIDER_EXISTS, rider.getName()));
        }

        this.data.add(rider);
    }

    @Override
    public boolean remove(Rider rider) {
        return this.data.removeIf(rider1 -> rider1.getName().equals(rider.getName()));
    }
}
