package cresla.entities;

public class HeatReactor extends BaseReactor {
    private int heatReductionIndex;

    public HeatReactor(int id, int heatReductionIndex, int maxCapacity) {
        super(id, maxCapacity);
        this.heatReductionIndex = heatReductionIndex;
    }

    @Override
    public long getTotalHeatAbsorbing() {
        return super.getTotalHeatAbsorbing() + this.heatReductionIndex;
    }
}
