package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.Collection;

public class MissionImpl implements Mission {
    public MissionImpl() { }

    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        for (Astronaut astronaut : astronauts) {
            if (astronaut.canBreath()) {
                continue;
            }

            while (planet.getItems().size() != 0) {
                for (String item : planet.getItems()) {
                    astronaut.breath();
                    astronaut.getBag().getItems().add(item);
                    planet.getItems().remove(item);

                    if (astronaut.canBreath()) {
                        break;
                    }
                }

                if (astronaut.canBreath()) {
                    break;
                }
            }
        }
    }
}
