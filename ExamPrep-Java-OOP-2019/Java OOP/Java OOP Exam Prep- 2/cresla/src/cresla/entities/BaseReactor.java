package cresla.entities;

import cresla.entities.containers.ModuleContainer;
import cresla.interfaces.AbsorbingModule;
import cresla.interfaces.Container;
import cresla.interfaces.EnergyModule;
import cresla.interfaces.Reactor;

public abstract class BaseReactor implements Reactor {
    private int id;
    private Container container;

    protected BaseReactor(int id, int maxCapacity) {
        this.id = id;
        this.container = new ModuleContainer(maxCapacity);
    }

    protected Container getContainer() {
        return this.container;
    }

    @Override
    public int getModuleCount() {
        return this.container.getModuleByInputCount();
    }

    @Override
    public void addEnergyModule(EnergyModule energyModule) {
        this.container.addEnergyModule(energyModule);
    }

    @Override
    public void addAbsorbingModule(AbsorbingModule absorbingModule) {
        this.container.addAbsorbingModule(absorbingModule);
    }

    @Override
    public long getTotalEnergyOutput() {
        return this.container.getTotalEnergyOutput();
    }

    @Override
    public long getTotalHeatAbsorbing() {
        return this.container.getTotalHeatAbsorbing();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(" - ").append(this.getId())
            .append(System.lineSeparator())
            .append("Energy Output: ").append(this.getTotalEnergyOutput())
            .append(System.lineSeparator())
            .append("Heat Absorbing: ").append(this.getTotalHeatAbsorbing())
            .append(System.lineSeparator())
            .append("Modules: ").append(this.getModuleCount());

        return sb.toString();
    }
}
