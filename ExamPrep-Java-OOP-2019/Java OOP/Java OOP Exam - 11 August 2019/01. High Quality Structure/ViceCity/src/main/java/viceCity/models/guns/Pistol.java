package viceCity.models.guns;

public class Pistol extends BaseGun {
    private final static int BULLETS_PER_BARREL = 10;
    private final static int TOTAL_BULLETS = 100;

    public Pistol(String name) {
        super(name, BULLETS_PER_BARREL, TOTAL_BULLETS);
    }

    @Override
    public int fire() {
        if (super.getBulletsPerBarrel() > 0) {
            super.setBulletsPerBarrel(super.getBulletsPerBarrel() - 1);
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

        return 1;
    }

}
