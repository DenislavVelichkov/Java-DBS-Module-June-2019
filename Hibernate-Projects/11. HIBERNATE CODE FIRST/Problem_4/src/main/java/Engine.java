import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Scanner;

public class Engine implements Runnable{
    private EntityManager entityManager;
    private Scanner scanner = new Scanner(System.in);

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        /*String input = scanner.nextLine();
        String patientName = "";
        String patientLastName = "";
        String patientAddress = "";
        LocalDate dateOfBirth;
        String email = "";

        while (input.equals("EXIT")) {
 System.out.println(
            "Hello Doc! Before you continue with your visit," +
                " you must fill some data about your patient.");

        System.out.print("Please enter your patient's First Name: ");
        patientName = scanner.nextLine();
            input = patientName;
        System.out.print("Please enter your patient's Last Name: ");
        patientLastName = scanner.nextLine();
        System.out.print("Please enter your patient's Address: ");
        patientAddress = scanner.nextLine();
        System.out.print("Please enter your patient's Birth Date in format(y-m-d): ");
        dateOfBirth = LocalDate.parse(scanner.nextLine());
        System.out.print("Please enter your patient's email: ");
        email = scanner.nextLine();

            TO BE CONTINUED...... :)
    }*/


    }
}
