package cresla.entities;

import cresla.interfaces.AbsorbingModule;

public abstract class BaseHeatAbsorbingModule extends BaseModule implements AbsorbingModule {
    private int heatAbsorbing;

    protected BaseHeatAbsorbingModule(int id, int heatAbsorbing) {
        super(id);
        this.heatAbsorbing = heatAbsorbing;
    }

    @Override
    public int getHeatAbsorbing() {
        return this.heatAbsorbing;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(" Module").append(" - ").append(this.getId())
            .append(System.lineSeparator());
        sb.append("Heat Absorbing: ").append(this.getHeatAbsorbing());

        return sb.toString();
    }
}
