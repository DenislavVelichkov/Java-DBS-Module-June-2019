package viceCity.core;

import viceCity.common.ConstantMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private final Player mainPlayer;
    private final Set<Player> civilPlayers;
    private final Deque<Gun> gunRepository;
    private final Neighbourhood neighbourhood;

    public ControllerImpl() {
        this.neighbourhood= new GangNeighbourhood();
        this.mainPlayer = new MainPlayer();
        this.civilPlayers = new HashSet<>();
        this.gunRepository = new ArrayDeque<>();
    }

    @Override
    public String addPlayer(String name) {
        Player civilPlayer = new CivilPlayer(name);
        this.civilPlayers.add(civilPlayer);

        return String.format(ConstantMessages.PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gunToAdd;

        if (type.equals("Pistol")) {
            gunToAdd = new Pistol(name);
            this.gunRepository.push(gunToAdd);
            return String.format(ConstantMessages.GUN_ADDED, name, type);
        }
        if (type.equals("Rifle")) {
            gunToAdd = new Rifle(name);
            this.gunRepository.push(gunToAdd);
            return String.format(ConstantMessages.GUN_ADDED, name, type);
        }

        return ConstantMessages.GUN_TYPE_INVALID;
    }

    @Override
    public String addGunToPlayer(String name) {
        if (this.gunRepository.isEmpty()) {
            return ConstantMessages.GUN_QUEUE_IS_EMPTY;
        }

        if (name.contains("Vercetti")) {
            this.mainPlayer.getGunRepository().add(this.gunRepository.peekLast());
            return String.format(ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER,
                this.gunRepository.pollLast().getName(), this.mainPlayer.getName());
        }

        Player civilPlayer =
            this.civilPlayers.stream()
                .filter(player -> player.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (civilPlayer == null) {
            return ConstantMessages.CIVIL_PLAYER_DOES_NOT_EXIST;
        }

        civilPlayer.getGunRepository().add(gunRepository.peekLast());

        return String.format(ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER,
            this.gunRepository.pollLast().getName(), name);
    }

    @Override
    public String fight() {
        this.neighbourhood.action(mainPlayer, civilPlayers);

        List<Player> civilPlayersKilled = this.civilPlayers
            .stream()
            .filter(player -> player.getLifePoints() <= 0)
            .collect(Collectors.toCollection(ArrayList::new));

       int civilPlayersDamaged =
           (int) this.civilPlayers
               .stream()
               .filter(player -> player.getLifePoints() <= 50 && player.getLifePoints() > 0)
               .count();

        if (civilPlayersKilled.size() == 0 &&
            mainPlayer.getLifePoints() == 100) {
            return ConstantMessages.FIGHT_HOT_HAPPENED;
        }

        StringBuilder sb = new StringBuilder();
        if (civilPlayersDamaged != 0 || civilPlayersKilled.size() != 0) {

            sb.append(ConstantMessages.FIGHT_HAPPENED).append(System.lineSeparator())
                .append(String.format(ConstantMessages.MAIN_PLAYER_LIVE_POINTS_MESSAGE,
                    this.mainPlayer.getLifePoints())).append(System.lineSeparator())
                .append(String.format(ConstantMessages.MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE,
                    civilPlayersKilled.size())).append(System.lineSeparator())
            .append(String.format(ConstantMessages.CIVIL_PLAYERS_LEFT_MESSAGE, civilPlayersDamaged));
            this.civilPlayers.removeAll(civilPlayersKilled);
        }

        return sb.toString();
    }
}
