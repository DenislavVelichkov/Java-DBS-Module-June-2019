package models.players;

import common.ConstantMessages;
import models.players.interfaces.Player;
import repositories.interfaces.CardRepository;

public abstract class BasePlayer implements Player {
    private String username;
    private int health;
    private final CardRepository cardRepository;

    protected BasePlayer(CardRepository cardRepository, String username, int health) {
        this.setUsername(username);
        this.setHealth(health);
        this.cardRepository = cardRepository;
    }

    private void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Player's username cannot be null or an empty string.");
        }
        this.username = username;
    }

    @Override
    public CardRepository getCardRepository() {
        return this.cardRepository;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int healthPoints) {
        if (healthPoints < 0) {
            throw new IllegalArgumentException("Player's health bonus cannot be less than zero.");
        }
        this.health = healthPoints;
    }

    @Override
    public boolean isDead() {
        return this.getHealth() <= 0;
    }

    @Override
    public void takeDamage(int damagePoints) {
        if (damagePoints < 0) {
            throw new IllegalArgumentException("Damage points cannot be less than zero.");
        }

        this.health -= damagePoints;

        if (this.health <= 0) {
            this.health = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(ConstantMessages.PLAYER_REPORT_INFO,
                this.username, this.health, this.cardRepository.getCount()))
                .append(System.lineSeparator());

        this.cardRepository.getCards().forEach(card -> sb.append(card.toString())
                .append(System.lineSeparator()));

        sb.append(ConstantMessages.DEFAULT_REPORT_SEPARATOR)
                .append(System.lineSeparator());

        return sb.toString().trim();
    }
}
