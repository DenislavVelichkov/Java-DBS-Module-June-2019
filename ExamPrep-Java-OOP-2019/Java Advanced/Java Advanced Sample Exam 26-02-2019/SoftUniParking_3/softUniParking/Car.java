package JavaAdvanced.ExamPrep_24.SoftUniParking_3.softUniParking;

public class Car {
   private String make;
   private String model;
   private int horsePower;
   private String registrationNumber;

   public Car(String make,
              String model,
              int horsePower,
              String registrationNumber) {
      this.make = make;
      this.model = model;
      this.horsePower = horsePower;
      this.registrationNumber = registrationNumber;
   }

   public String getRegistrationNumber() {
      return this.registrationNumber;
   }

   public String getMake() {
      return this.make;
   }

   public String getModel() {
      return this.model;
   }

   public int getHorsePower() {
      return this.horsePower;
   }

   @Override
   public String toString() {
      return String.format("Make: %s%n" +
              "Model: %s%n" +
              "HorsePower: %d%n" +
              "RegistrationNumber: %s",
              this.make, this.model,
              this.horsePower, this.registrationNumber);
   }
}
