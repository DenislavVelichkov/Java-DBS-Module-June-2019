package arena;

public class Gladiator {
    private String name;
    private Stat stat;
    private Weapon weapon;

    public Gladiator(String name, Stat stat, Weapon weapon) {
        this.name = name;
        this.stat = stat;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public int getStatPower() {
        return this.stat.getAgility() +
                this.stat.getFlexibility() +
                this.stat.getIntelligence() +
                this.stat.getStrength();
    }

    public int getWeaponPower() {
        return this.weapon.getSharpness() +
                this.weapon.getSize() +
                this.weapon.getSolidity();
    }

    public int getTotalPower() {
        return this.getStatPower() + this.getWeaponPower();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s – %d", this.name, this.getTotalPower()))
                .append(System.lineSeparator())
                .append(String.format("\tWeapon Power: %d", this.getWeaponPower()))
                .append(System.lineSeparator())
                .append(String.format("\tStat Power: %d", this.getStatPower()));

        return sb.toString();
    }
}
