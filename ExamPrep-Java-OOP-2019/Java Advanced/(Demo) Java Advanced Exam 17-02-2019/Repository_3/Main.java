package JavaAdvanced.DemoExam_17_02_19.Repository_3;

import JavaAdvanced.DemoExam_17_02_19.Repository_3.repository.Person;
import JavaAdvanced.DemoExam_17_02_19.Repository_3.repository.Repository;

public class Main {
    public static void main(String[] args) {
        //Initialize the JavaAdvanced.DemoExam_17_02_19.Repository_3.repository
        Repository repository = new Repository();

        //Initialize JavaOOP.Inheritance_7.Exercise.Person_1.Human
        Person person = new Person("Pesho", 14, "13-07-2004");

        //AddCommand two entities
        repository.add(person);

        //Initialize second JavaOOP.Inheritance_7.Exercise.Person_1.Human object
        Person secondPerson = new Person("Gosho", 42, "21-09-1976");
        repository.add(secondPerson);

        System.out.println(repository.get(0).toString());
        //Name: Pesho
        //Age: 14
        //Birthday: 13-07-2004

        System.out.println(repository.get(1).toString());
        //Name: Gosho
        //Age: 42
        //Birthday: 21-09-1976


        //Update person with id 1
            repository.update(1, new Person("Success", 20, "01-01-1999"));

        System.out.println(repository.get(1).toString());
        //Name: Success
        //Age: 20
        //Birthday: 01-01-1999

        //Delete entity
        repository.delete(0);

        System.out.println(repository.getCount());
        //1
    }
}
