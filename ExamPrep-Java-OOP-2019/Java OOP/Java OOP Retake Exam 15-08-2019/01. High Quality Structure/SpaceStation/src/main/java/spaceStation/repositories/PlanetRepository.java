package spaceStation.repositories;

import spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class PlanetRepository implements Repository<Planet> {
    private Collection<Planet> planets;

    public PlanetRepository() {
        this.planets = new LinkedList<>();
    }

    @Override
    public Collection<Planet> getModels() {
        return Collections.unmodifiableCollection(this.planets);
    }

    @Override
    public void add(Planet model) {
            this.planets.add(model);
    }

    @Override
    public boolean remove(Planet model) {
        return this.planets.removeIf(pl -> pl.getName().equals(model.getName()));
    }

    @Override
    public Planet findByName(String name) {
        return this.planets
            .stream()
            .filter(planet -> planet.getName().equals(name))
            .findFirst()
            .orElse(null);
    }
}
