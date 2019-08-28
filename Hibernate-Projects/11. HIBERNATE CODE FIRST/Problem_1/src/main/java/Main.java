import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("wizards");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        WizardDeposits testRoll = new WizardDeposits(
            "Pesho", "Peshev", "alabala", -10, "Peshev", 242, "aaa", LocalDateTime.now(), 1000.0f, 10.0f, 15.0f, LocalDateTime.now(), false);

        entityManager.getTransaction().begin();
        entityManager.persist(testRoll);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

}
