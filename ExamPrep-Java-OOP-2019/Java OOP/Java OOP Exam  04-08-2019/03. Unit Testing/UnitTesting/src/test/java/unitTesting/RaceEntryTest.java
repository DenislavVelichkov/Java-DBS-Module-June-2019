package unitTesting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RaceEntryTest {
    RaceEntry raceEntry;

    @Before
    public void setUp() {
        this.raceEntry = new RaceEntry();
    }

    @Test(expected = NullPointerException.class)
    public void addingRiderWithInvalidValueNull() {
        this.raceEntry.addRider(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDuplicateUnite() {
        UnitRider rider = new UnitRider("Stamat", null);
        UnitRider rider2 = new UnitRider("Stamat", null);

        this.raceEntry.addRider(rider);
        this.raceEntry.addRider(rider2);
    }

    @Test
    public void addShouldReturnCorrectMessage() {
        UnitRider rider = new UnitRider("Stamat", null);
        String result = this.raceEntry.addRider(rider);
        String expected = "Rider Stamat added in race.";
        Assert.assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateAverageHorsepowerWithIncorrectParticipants() {
        this.raceEntry.addRider(new UnitRider("Stancho",
                new UnitMotorcycle("Suzuki", 140, 1100)));
        this.raceEntry.calculateAverageHorsePower();
    }

    @Test
    public void calculateAvgHpMustWorkCorrectly() {
        UnitRider rider = new UnitRider("Stancho",
                new UnitMotorcycle("Suzuki", 100, 450));
        UnitRider rider2 = new UnitRider("Stancho2",
                new UnitMotorcycle("Suzuki", 100, 450));
        this.raceEntry.addRider(rider);
        this.raceEntry.addRider(rider2);
        double expected = this.raceEntry.calculateAverageHorsePower();

        Assert.assertEquals(expected, 100, 0.01);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmutableCollection() {
        UnitRider rider = new UnitRider("Stancho",
                new UnitMotorcycle("Suzuki", 140, 1100));
        this.raceEntry.addRider(rider);
        this.raceEntry.getRiders().remove(rider);
    }
}
