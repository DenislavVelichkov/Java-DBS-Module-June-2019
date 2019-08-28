package viceCity.repositories;

import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.repositories.interfaces.Repository;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class GunRepository implements Repository {
    private Deque<Gun> models;

    public  GunRepository() {
        this.models = new ArrayDeque<>();
    }

    @Override
    public Collection getModels() {
        return this.models;
    }

    @Override
    public void add(Object model) {
        boolean isPresent;

        if (model instanceof Pistol) {
            isPresent =
                this.models.stream()
                    .anyMatch(gun -> gun.getName().equals(((Pistol) model).getName()));
            if (!isPresent) {
                this.models.push((Pistol) model);
            }
        }
        if (model instanceof Rifle) {
            isPresent =
                this.models.stream()
                    .anyMatch(gun -> gun.getName().equals(((Rifle) model).getName()));
            if (!isPresent) {
                this.models.push((Rifle) model);
            }
        }
    }

    @Override
    public boolean remove(Object model) {
        Gun gunToRemove = null;

        if (model instanceof Pistol) {
            gunToRemove = (Pistol) this.find(((Pistol) model).getName());
        }
        if (model instanceof Rifle) {
            gunToRemove = (Rifle) this.find(((Rifle) model).getName());
        }

        return this.models.remove(gunToRemove);
    }

    @Override
    public Object find(String name) {
        return this.models.stream().filter(gun -> gun.getName().equals(name)).findAny().orElse(null);
    }
}
