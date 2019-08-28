package spaceStation.core;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;
import spaceStation.repositories.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private static int exploredPlanetsCount = 0;
    private static final double MIN_OXYGEN_LEVEL_FOR_MISSION = 60d;
    private Repository<Astronaut> astronautRepository;
    private Repository<Planet> planetRepository;
    private Mission mission;

   public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
        this.mission = new MissionImpl();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut = null;

        if (type.equals("Biologist")) {
            astronaut = new Biologist(astronautName);
        }
        if (type.equals("Geodesist")) {
            astronaut = new Geodesist(astronautName);
        }
        if (type.equals("Meteorologist")) {
            astronaut = new Meteorologist(astronautName);
        }
        if (astronaut == null) {
            throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_INVALID_TYPE);
        }

        this.astronautRepository.add(astronaut);

        return String.format(ConstantMessages.ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        List<String> itemsToAdd =
                Arrays.stream(items)
                        .filter(s -> s != null && !s.trim().isEmpty())
                        .collect(Collectors.toList());
        planet.getItems().addAll(itemsToAdd);
        this.planetRepository.add(planet);

        return String.format(ConstantMessages.PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        Astronaut astronautToRemove =
                this.astronautRepository.findByName(astronautName);

        if (astronautToRemove == null) {
            throw new IllegalArgumentException(
                    String.format(ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }

        this.astronautRepository.remove(astronautToRemove);

        return String.format(ConstantMessages.ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        List<Astronaut> astronautsReadyForMission = this.astronautRepository
                        .getModels()
                        .stream()
                        .filter(astronaut -> astronaut.getOxygen() > MIN_OXYGEN_LEVEL_FOR_MISSION)
                        .collect(Collectors.toCollection(ArrayList::new));

        if (astronautsReadyForMission.size() == 0) {
            throw new IllegalArgumentException(
                    ExceptionMessages.PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }

        Planet planetToExplore = this.planetRepository.findByName(planetName);

        this.mission.explore(planetToExplore, astronautsReadyForMission);

        int deadAstronauts = (int) astronautsReadyForMission
                .stream()
                .filter(astronaut -> astronaut.canBreath())
                .count();

        exploredPlanetsCount++;

        return String.format(ConstantMessages.PLANET_EXPLORED, planetName, deadAstronauts);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                ConstantMessages.REPORT_PLANET_EXPLORED, exploredPlanetsCount))
                .append(System.lineSeparator())
                .append(ConstantMessages.REPORT_ASTRONAUT_INFO)
                .append(System.lineSeparator());
        this.astronautRepository
                .getModels()
                .forEach(astronaut ->
                        sb.append(astronaut.toString()).append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
