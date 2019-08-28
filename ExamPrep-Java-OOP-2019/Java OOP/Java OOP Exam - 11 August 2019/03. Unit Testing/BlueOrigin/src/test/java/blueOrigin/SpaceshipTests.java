package blueOrigin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpaceshipTests {
    private Astronaut astronaut1;
    private Astronaut astronaut2;
    private Astronaut astronaut3;
    private Spaceship spaceship;

    @Before
    public void setUp() {
        this.astronaut1 = new Astronaut("Peshko", 10.0);
        this.astronaut2 = new Astronaut("Strahil", 9.5);
        this.astronaut3 = new Astronaut("Zaprqn", 8.5);
        this.spaceship = new Spaceship("Arcadia", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSpaceshipWithInvalidCapacity() {
        new Spaceship("P", -10);
    }

    @Test(expected = NullPointerException.class)
    public void createSpaceshipWithInvalidName() {
        new Spaceship(null, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAstronautOverCapacity() {
        this.spaceship.add(this.astronaut1);
        this.spaceship.add(this.astronaut2);
        this.spaceship.add(this.astronaut3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAstronautWithSameName() {
        this.spaceship.add(this.astronaut1);
        this.spaceship.add(this.astronaut1);
    }

    @Test
    public void removeAstronautTrue() {
        this.spaceship.add(astronaut1);
        Assert.assertTrue(this.spaceship.remove("Peshko"));
    }

    @Test
    public void removeAstronautFalse() {
        this.spaceship.add(astronaut1);
        Assert.assertFalse(this.spaceship.remove(null));
    }


}
