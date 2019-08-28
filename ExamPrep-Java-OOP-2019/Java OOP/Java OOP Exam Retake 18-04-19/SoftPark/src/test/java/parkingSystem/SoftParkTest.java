package parkingSystem;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SoftParkTest {
    private Car car;
    private Car newCar;
    private SoftPark parking;
    
    @Before
    public void setUpCar() {
        this.car = new Car("Honda", "CA1111BG");
        this.newCar = new Car("Jigula", "СХ3449АС");
        
        this.parking = new SoftPark();
    }
    
    @Test
    public void checkIfUnmodifiableList() {
        Map<String, Car> parking = new HashMap<>();
        parking.put("A1", null);
        parking.put("A2", null);
        parking.put("A3", null);
        parking.put("A4", null);
        parking.put("B1", null);
        parking.put("B2", null);
        parking.put("B3", null);
        parking.put("B4", null);
        parking.put("C1", null);
        parking.put("C2", null);
        parking.put("C3", null);
        parking.put("C4", null);

        Assert.assertEquals(parking, this.parking.getParking());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parkCarSpotShouldThrowExceptionIfDoesntExists() {
        this.parking.parkCar("Z11", this.car);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void parkCarIfSpotIsNotTaken() {
        this.parking.parkCar("A3", this.car);
        this.parking.parkCar("A3", this.newCar);
    }
    
    @Test(expected = IllegalStateException.class)
    public void parkSpotShouldNotBeTaken() {
        this.parking.parkCar("A3", this.car);
        this.parking.parkCar("A4", this.car);
    }
    
    @Test
    public void parkCarShouldWorkCorrectly() {
        this.parking.parkCar("A3", this.car);
        Assert.assertEquals(this.car.getMake(), this.parking.getParking().get("A3").getMake());
    }
    
    @Test
    public void carIsParkedSuccessfully() {
        Assert.assertTrue(this.car.getRegistrationNumber(), this.parking.parkCar("A3", this.car).contains(this.car.getRegistrationNumber()));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void removeCarShouldThrowExceptionIfDoesntExists() {
        this.parking.removeCar("Z11", this.car);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void removeCarShouldThrowExceptionIfTheCarIsDifferent() {
        this.parking.parkCar("A3", this.newCar);
        this.parking.removeCar("A3", this.car);
    }
    
    @Test
    public void removeCarShouldWorkCorrectly() {
        this.parking.parkCar("A3", newCar);
        this.parking.removeCar("A3", newCar);
        Assert.assertNull(this.parking.getParking().get("A3"));
        
    }
    
    @Test
    public void removeCarShouldReturnCorrectMassage() {
        this.parking.parkCar("A3", newCar);
        Assert.assertTrue(this.newCar.getRegistrationNumber(), this.parking.removeCar("A3", newCar).contains(this.newCar.getRegistrationNumber()));
    }
}
