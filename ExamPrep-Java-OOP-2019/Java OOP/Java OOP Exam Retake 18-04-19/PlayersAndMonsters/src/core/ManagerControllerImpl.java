package core;

import common.ConstantMessages;
import core.interfaces.ManagerController;
import models.battleFields.BattleFieldImpl;
import models.battleFields.interfaces.Battlefield;
import models.cards.MagicCard;
import models.cards.TrapCard;
import models.cards.interfaces.Card;
import models.players.Advanced;
import models.players.Beginner;
import models.players.interfaces.Player;
import repositories.CardRepositoryImpl;
import repositories.PlayerRepositoryImpl;
import repositories.interfaces.CardRepository;
import repositories.interfaces.PlayerRepository;


public class ManagerControllerImpl implements ManagerController {
    private PlayerRepository playerRepository;
    private Battlefield battleField;
    private CardRepository cardRepository;

    public ManagerControllerImpl() {
        this.cardRepository = new CardRepositoryImpl();
        this.playerRepository = new PlayerRepositoryImpl();
        this.battleField = new BattleFieldImpl();
    }

    @Override
    public String addPlayer(String type, String username) {
        Player newPlayer = null;

        if (type.equals("Beginner")) {
            newPlayer = new Beginner(new CardRepositoryImpl(), username);
        }
        if (type.equals("Advanced")) {
            newPlayer = new Advanced(new CardRepositoryImpl(), username);
        }

        this.playerRepository.add(newPlayer);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER, type, username);
    }

    @Override
    public String addCard(String type, String name) {
        Card newCard = null;

        if (type.equals("Magic")) {
            newCard = new MagicCard(name);
        }

        if (type.equals("Trap")) {
            newCard = new TrapCard(name);
        }

        this.cardRepository.add(newCard);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CARD, type, name);
    }

    @Override
    public String addPlayerCard(String username, String cardName) {
        Card cardToAdd = this.cardRepository.find(cardName);
        Player player = this.playerRepository.find(username);

        String resultMsg =
            String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER_WITH_CARDS, cardName, username);

        if (cardToAdd == null) {
            resultMsg = "Card cannot be null!";
            return resultMsg;
        }
        if (player == null) {
            resultMsg = "Player cannot be null!";
            return resultMsg;
        }

        player.getCardRepository().add(cardToAdd);

        return resultMsg;
    }

    @Override
    public String fight(String attackUser, String enemyUser) {
        Player attackingPlayer = this.playerRepository.find(attackUser);
        Player enemyPlayer = this.playerRepository.find(enemyUser);

        this.battleField.fight(attackingPlayer, enemyPlayer);

        return String.format(ConstantMessages.FIGHT_INFO,
            attackingPlayer.getHealth(), enemyPlayer.getHealth());
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        this.playerRepository
                .getPlayers()
                .forEach(player -> sb.append(player.toString()).append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
