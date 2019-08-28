import models.BankAccount;
import models.BillingDetail;
import models.CreditCard;
import models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("billing_system");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        User user = new User("Peshko", "Paraleev", "deathRowRecords@gmail.com", "1234Amaizing!");
        User user1 = new User("Gatso", "Gatsev", "brokeThugsClub@gmail.com", "Mamamia!");

        BillingDetail creditCardPayment = new CreditCard("Novi banski", user, "VISA", "07", "2020");
        BillingDetail bankAccountPayment = new BankAccount("Novi shorti", user1, "Reifeisen Bank", "DSAF215");

        entityManager.getTransaction().begin();

        entityManager.persist(creditCardPayment);
        entityManager.persist(bankAccountPayment);

        user.getBillingDetail().add(creditCardPayment);
        user1.getBillingDetail().add(bankAccountPayment);

        entityManager.persist(user);
        entityManager.persist(user1);

        entityManager.getTransaction().commit();


        entityManager.close();
        entityManagerFactory.close();
    }
}
