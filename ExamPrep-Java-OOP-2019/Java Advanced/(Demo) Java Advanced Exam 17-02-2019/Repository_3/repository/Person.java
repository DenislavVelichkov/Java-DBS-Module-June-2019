package JavaAdvanced.DemoExam_17_02_19.Repository_3.repository;

public class Person {
    private String name;
    private int age;
    private String birthDate;

    public Person(String name, int age, String birthDate) {
        this.name = name;
        this.age = age;
        this.birthDate = birthDate;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    @Override
    public String toString() {
        return "Name: " +
                this.name +
                System.lineSeparator() +
                "Age: " +
                this.age +
                System.lineSeparator() +
                "Birthday: " +
                this.birthDate +
                System.lineSeparator();
    }
}
