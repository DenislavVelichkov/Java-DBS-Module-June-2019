package cresla.entities;

public class CryoReactor extends BaseReactor {
    private int cryoProductionIndex;

    public CryoReactor(int id, int cryoProductionIndex, int maxCapacity) {
        super(id, maxCapacity);
        this.cryoProductionIndex = cryoProductionIndex;
    }

    @Override
    public long getTotalEnergyOutput() {
        long totalEnergyOutput;
        if (super.getTotalHeatAbsorbing() != 0) {
            totalEnergyOutput =
                super.getTotalEnergyOutput() * this.cryoProductionIndex <= super.getTotalHeatAbsorbing()
                    ? super.getTotalEnergyOutput() * this.cryoProductionIndex
                    : 0L;
        } else {
            totalEnergyOutput = super.getTotalEnergyOutput() * this.cryoProductionIndex;
        }

        return totalEnergyOutput;
    }
}
