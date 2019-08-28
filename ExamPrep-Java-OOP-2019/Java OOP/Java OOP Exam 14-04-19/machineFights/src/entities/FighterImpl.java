package entities;

import entities.interfaces.Fighter;

public class FighterImpl extends BaseMachine implements Fighter {
    private static double ATTACK_MODIFIER = 50.0;
    private static double DEFENCE_MODIFIER = 25.0;
    private boolean aggressiveMode;

    public FighterImpl(String name, double attackPoints, double defensePoints){
        super(name, attackPoints + ATTACK_MODIFIER, defensePoints - DEFENCE_MODIFIER);
        super.setHealthPoints(200.0);
        this.aggressiveMode = true;

    }
    
    @Override
    public boolean getAggressiveMode() {
        return this.aggressiveMode;
    }
    
    @Override
    public void toggleAggressiveMode() {
        this.aggressiveMode = !this.aggressiveMode;
        
        if (this.aggressiveMode) {
            super.setAttackPoints(super.getAttackPoints() + ATTACK_MODIFIER);
            super.setDefensePoints(super.getDefensePoints() - DEFENCE_MODIFIER);
        } else {
            super.setAttackPoints(super.getAttackPoints() - ATTACK_MODIFIER);
            super.setDefensePoints(super.getDefensePoints() + DEFENCE_MODIFIER);
        }
        
    }
    
    @Override
    public String toString() {
        String aggressiveMode = "(ON)";
        
        if (!this.aggressiveMode) {
            aggressiveMode = "(OFF)";
        }
        
        return super.toString() + String.format("%n *Aggressive Mode%s", aggressiveMode);
    }
}
