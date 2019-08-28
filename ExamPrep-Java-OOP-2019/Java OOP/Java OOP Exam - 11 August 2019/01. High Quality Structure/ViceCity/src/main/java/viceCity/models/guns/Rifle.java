package viceCity.models.guns;

public class Rifle extends BaseGun {
    private final static int BULLETS_PER_BARREL = 50;
    private final static int TOTAL_BULLETS = 500;

    public Rifle(String name) {
        super(name, BULLETS_PER_BARREL, TOTAL_BULLETS);
    }

    @Override
    public int fire() {
        if (super.getBulletsPerBarrel() > 0) {
            super.setBulletsPerBarrel(super.getBulletsPerBarrel() - 5);
        } else if (super.getTotalBullets() != 0) {
            if (super.getTotalBullets() - BULLETS_PER_BARREL < 0) {
                super.setTotalBullets(0);
            } else {
                super.setTotalBullets(super.getTotalBullets() - BULLETS_PER_BARREL);
            }
            super.setBulletsPerBarrel(BULLETS_PER_BARREL);
        } else {
            return 0;
        }

        return 5;
    }
}
