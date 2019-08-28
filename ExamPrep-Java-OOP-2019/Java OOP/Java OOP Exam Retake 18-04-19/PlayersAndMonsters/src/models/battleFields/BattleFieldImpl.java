package models.battleFields;

import models.battleFields.interfaces.Battlefield;
import models.cards.interfaces.Card;
import models.players.Beginner;
import models.players.interfaces.Player;

public class BattleFieldImpl implements Battlefield {

    @Override
    public void fight(Player attackPlayer, Player enemyPlayer) {
        if (attackPlayer.isDead() || enemyPlayer.isDead()) {
            throw new IllegalArgumentException("Player is dead!");
        }

        if (attackPlayer instanceof Beginner) {
            attackPlayer.setHealth(attackPlayer.getHealth() + 40);
            attackPlayer.getCardRepository().getCards().forEach(card -> card.setDamagePoints(card.getDamagePoints() + 30));

        }

        if (enemyPlayer instanceof Beginner) {
            enemyPlayer.setHealth(enemyPlayer.getHealth() + 40);
            enemyPlayer.getCardRepository().getCards().forEach(card -> card.setDamagePoints(card.getDamagePoints() + 30));

        }

        attackPlayer.setHealth(attackPlayer.getHealth () + (int) attackPlayer.getCardRepository().getCards().stream().mapToLong(Card::getHealthPoints).sum());
        enemyPlayer.setHealth(enemyPlayer.getHealth () + (int) enemyPlayer.getCardRepository().getCards().stream().mapToLong(Card::getHealthPoints).sum());
        long attackPlayerDamage = attackPlayer.getCardRepository().getCards().stream().mapToLong(Card::getDamagePoints).sum();
        long enemyPlayerDamage = enemyPlayer.getCardRepository().getCards().stream().mapToLong(Card::getDamagePoints).sum();

        while (true) {
            enemyPlayer.takeDamage((int) attackPlayerDamage);
            if (enemyPlayer.isDead()) {
                break;
            }

            attackPlayer.takeDamage((int) enemyPlayerDamage);
            if (attackPlayer.isDead()) {
                break;
            }
        }

    }
}