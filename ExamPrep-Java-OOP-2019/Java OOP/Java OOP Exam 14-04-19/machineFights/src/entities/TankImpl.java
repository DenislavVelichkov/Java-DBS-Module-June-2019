package entities;

import entities.interfaces.Tank;

public class TankImpl extends BaseMachine implements Tank {
    private static double ATTACK_MODIFIER = 40.0;
    private static double DEFENCE_MODIFIER = 30.0;
    private boolean defenceMode;

    public TankImpl(String name, double attackPoints, double defensePoints){
        super(name, attackPoints - ATTACK_MODIFIER, defensePoints + DEFENCE_MODIFIER);
        super.setHealthPoints(100);
        this.defenceMode = true;
    }
    
    @Override
    public boolean getDefenseMode() {
        return this.defenceMode;
    }
    
    @Override
    public void toggleDefenseMode() {
        this.defenceMode = !this.defenceMode;
    
        if (this.defenceMode) {
            super.setAttackPoints(super.getAttackPoints() - ATTACK_MODIFIER);
            super.setDefensePoints(super.getDefensePoints() + DEFENCE_MODIFIER);
        } else {
            super.setAttackPoints(super.getAttackPoints() + ATTACK_MODIFIER);
            super.setDefensePoints(super.getDefensePoints() - DEFENCE_MODIFIER);
        }
    }
    @Override
    public String toString() {
        String defensiveMode = "(ON)" + System.lineSeparator();
        
        if (!this.defenceMode) {
            defensiveMode = "(OFF)" + System.lineSeparator();
        }
        
        return super.toString() + String.format("%n *Defense Mode%s", defensiveMode);
    }
    
}
