package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood {
    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Player civilPlayer : civilPlayers) {
            for (Gun gun : mainPlayer.getGunRepository().getModels()) {
                while (gun.canFire() && civilPlayer.isAlive()) {
                    civilPlayer.takeLifePoints(gun.fire());
                }

                if (!civilPlayer.isAlive()) {
                    break;
                }
            }
        }

        for (Player civilPlayer : civilPlayers) {
            if (!civilPlayer.isAlive()) {
                continue;
            }

            for (Gun gun : civilPlayer.getGunRepository().getModels()) {
                while (gun.canFire() && mainPlayer.isAlive()) {
                    mainPlayer.takeLifePoints(gun.fire());
                }

                if (!mainPlayer.isAlive()) {
                    return;
                }
            }
        }
    }
}

