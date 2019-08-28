package hell.entities;

import hell.entities.miscellaneous.HeroInventory;
import hell.interfaces.Hero;
import hell.interfaces.Item;
import hell.interfaces.Recipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public abstract class Heroes implements Hero {
    private String name;
    private int strength;
    private int agility;
    private int intelligence;
    private int hitPoints;
    private int damage;
    private HeroInventory heroInventory;
    private Collection<Item> items;

    Heroes(String name,
           int strength,
           int agility,
           int intelligence,
           int hitPoints,
           int damage) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.heroInventory = new HeroInventory();
        this.items = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getStrength() {
        return this.strength + this.heroInventory.getTotalStrengthBonus();
    }

    @Override
    public long getAgility() {
        return this.agility + this.heroInventory.getTotalAgilityBonus();
    }

    @Override
    public long getIntelligence() {
        return this.intelligence + this.heroInventory.getTotalIntelligenceBonus();
    }

    @Override
    public long getHitPoints() {
        return this.hitPoints + this.heroInventory.getTotalHitPointsBonus();
    }

    @Override
    public long getDamage() {
        return this.damage + this.heroInventory.getTotalDamageBonus();
    }

    @Override
    public Collection<Item> getItems() throws NoSuchFieldException, IllegalAccessException {
        Field field = this.heroInventory.getClass().getDeclaredField("commonItems");
        field.setAccessible(true);
        this.items = ((Map<String, Item>) field.get(this.heroInventory)).values();

        return this.items;
    }

    @Override
    public void addItem(Item item) {
        this.heroInventory.addCommonItem(item);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        this.heroInventory.addRecipeItem(recipe);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Hero: ")
            .append(this.name + ", ")
            .append("Class: ")
            .append(this.getClass().getSimpleName())
            .append(System.lineSeparator())
            .append("HitPoints: " + this.getHitPoints() + ", ")
            .append("Damage: " + this.getDamage())
            .append(System.lineSeparator())
            .append("Strength: ").append(this.getStrength())
            .append(System.lineSeparator())
            .append("Agility: ").append(this.getAgility())
            .append(System.lineSeparator())
            .append("Intelligence: ").append(this.getIntelligence())
            .append(System.lineSeparator())
            .append("Items: ");

        if (this.items.isEmpty()) {
            sb.append("None");
        } else {
            sb.append(System.lineSeparator());
            this.items.forEach(item -> sb.append(item.toString()));
        }

        return sb.toString();
    }
}
