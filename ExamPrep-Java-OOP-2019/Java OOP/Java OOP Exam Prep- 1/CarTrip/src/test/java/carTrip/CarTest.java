package carTrip;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {
    private static final String MODEL = "Model";
    private static final double TANK_CAPACITY = 20.0;
    private static final double FUEL_AMOUNT = 10.0;
    private static final double FUEL_CONSUMPTION_PER_KM = 0.065;
    private static final double FUEL_AMOUNT_OVER_CAPACITY = 21.5;
    private static final double DISTANCE = 100.00;
    private static final double TRIP_CONSUMPTION = FUEL_CONSUMPTION_PER_KM * DISTANCE;
    private static final double REFUEL_AMOUNT = 10.0;
    private static final double REFUEL_AMOUNT_OVER_CAPACITY = 30.5;


    private Car car;

    @Before
    public void creatCar() {
        car = new Car(MODEL, TANK_CAPACITY, FUEL_AMOUNT, FUEL_CONSUMPTION_PER_KM);
    }

    @Test
    public void carConstructorMustFunctionCorrectly() {
        Car car = new Car(MODEL, TANK_CAPACITY, FUEL_AMOUNT, FUEL_CONSUMPTION_PER_KM);
    }

    @Test
    public void modelGetterShouldReturnCarModelCorrectly() {
        assertEquals(MODEL, car.getModel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void modelSetterShouldThrowIlliegalArgExceptionIfNullIsGivenForModel() {
        car.setModel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void modelSetterShouldThrowIlliegalArgExceptionIfEmptyStringIsGivenForModel() {
        car.setModel("");
    }

    @Test
    public void shouldReturnCorrectTankCapacity() {
        Assert.assertEquals(TANK_CAPACITY, car.getTankCapacity(), 0.0);
    }


    @Test
    public void shouldSetCorrectTankCapacity() {
        car.setTankCapacity(TANK_CAPACITY);
    }

    @Test
    public void shouldReturnCorrectFuelAmount() {
        Assert.assertEquals(FUEL_AMOUNT, car.getFuelAmount(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgExceptionIfFuelAmountIsMoreThenTankCapacity() {
        car.setFuelAmount(FUEL_AMOUNT_OVER_CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldSetCorrectFuelConsumption() {
        car.setFuelConsumptionPerKm(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgExceptionIfFuelAmountIsLessThenZero() {
        car.setFuelConsumptionPerKm(-1.1);
    }

    @Test
    public void shouldReturnCorrectTripConsumption() {
        Assert.assertEquals(TRIP_CONSUMPTION, car.getFuelConsumptionPerKm() * DISTANCE, 0.0);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldReturnIllegalStateExceptionIfFuelAmountIsLessThenTripConsumption() {
        car.drive(1000.0);
    }

    @Test
    public void shouldSetFuelAmountAfterTheTripCorrectly() {
        car.drive(DISTANCE);
        Assert.assertEquals(car.getFuelAmount(), FUEL_AMOUNT - TRIP_CONSUMPTION, 0.0);
    }

    @Test
    public void shouldReturnStringIfTheTripIsSuccessful() {
        Assert.assertEquals("Have a nice trip", car.drive(DISTANCE));
    }

    @Test
    public void shouldRefuelTheCarCorrectly() {
        car.refuel(REFUEL_AMOUNT);
        Assert.assertEquals(FUEL_AMOUNT + REFUEL_AMOUNT, car.getFuelAmount(), 0.0);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfRefuelAmountIsMoreThenTankCapacity() {
        car.refuel(REFUEL_AMOUNT_OVER_CAPACITY);
    }
}