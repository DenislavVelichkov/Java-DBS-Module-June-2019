package entities;

import entities.interfaces.Machine;
import entities.interfaces.Pilot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMachine implements Machine {
    private String name;
    private Pilot pilot;
    private double attackPoints;
    private double defensePoints;
    private double healthPoints;
    private List<String> targets;
    
   BaseMachine(String name, double attackPoints, double defensePoints) {
        this.setName(name);
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.targets = new ArrayList<>();
    }
    
    void setAttackPoints(double attackPoints) {
        this.attackPoints = attackPoints;
    }
    
    void setDefensePoints(double defensePoints) {
        this.defensePoints = defensePoints;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void setName(String name) throws IllegalArgumentException{
        if (name == null || name.isEmpty() || name.equals(" ")) {
            throw new IllegalArgumentException("Machine name cannot be null or empty.");
        }
    
        this.name = name;
    }
    
    @Override
    public Pilot getPilot() {
        return this.pilot;
    }
    
    @Override
    public void setPilot(Pilot pilot) throws NullPointerException{
        if (pilot == null) {
            throw new NullPointerException("Pilot cannot be null.");
        }
    
        this.pilot = pilot;
    }
    
    @Override
    public double getHealthPoints() {
        return this.healthPoints;
    }
    
    @Override
    public double getAttackPoints() {
        return this.attackPoints;
    }
    
    @Override
    public double getDefensePoints() {
        return this.defensePoints;
    }
    
    @Override
    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }
    
    @Override
    public List<String> getTargets() {
        return this.targets;
    }
    
    @Override
    public void attack(String target) throws IllegalArgumentException{
        if (target == null || target.isEmpty() || target.equals(" ")) {
            throw new IllegalArgumentException("Attack target cannot be null or empty string.");
        }
    
        this.getTargets().add(target);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String strToAppend = "None";
        String className = this.getClass().getSimpleName().substring(0, this.getClass().getSimpleName().length() - 4);

        if (this.getTargets().size() != 0) {
            strToAppend = String.join(", ", this.getTargets());
        }

        DecimalFormat df = new DecimalFormat("0.00");
        sb.append("- ").append(this.getName()).append(System.lineSeparator())
            .append(" *Type: ").append(className).append(System.lineSeparator())
            .append(" *Health: ").append(df.format(this.getHealthPoints())).append(System.lineSeparator())
            .append(" *Attack: ").append(df.format(this.getAttackPoints())).append(System.lineSeparator())
            .append(" *Defense: ").append(df.format(this.getDefensePoints())).append(System.lineSeparator())
            .append(" *Targets: ").append(strToAppend);

        return sb.toString();
    }
}
