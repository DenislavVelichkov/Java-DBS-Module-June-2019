package Introduction_To_Hibernate_7;

import Introduction_To_Hibernate_7.entities.Address;
import Introduction_To_Hibernate_7.entities.Employee;
import Introduction_To_Hibernate_7.entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {
    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        System.out.println("----------- Problem: 2 --------------");
        this.removeObjects();
        System.out.println("----------- Problem: 3 --------------");
        this.containsEmployee();
        System.out.println("----------- Problem: 6 --------------");
        this.addAddressAndUpdateEmployee();
        System.out.println("----------- Problem: 7 --------------");
        this.addressesWithEmployeeCount();
    }

    /*
     * Problem 2: Remove Objects
     * */
    private void removeObjects() {

        List<Town> towns = this.entityManager.createQuery("FROM Town", Town.class).getResultList();

        for (Town town : towns) {
            if (town.getName().length() > 5) {
                this.entityManager.detach(town);
            }
        }

        this.entityManager.getTransaction().begin();
        for (Town town : towns) {
            if (this.entityManager.contains(town)) {
                town.setName(town.getName().toLowerCase());
                this.entityManager.persist(town);
            }
        }

        this.entityManager.getTransaction().commit();

    }

    /*
     * Problem 3: Contains Employee
     * */
    private void containsEmployee() {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        this.entityManager.getTransaction().begin();

        try {
            Employee employee = this.entityManager
                                    .createQuery(
                                        "FROM Employee WHERE CONCAT(firstName, ' ', lastName) = :name", Employee.class)
                                    .setParameter("name", name)
                                    .getSingleResult();

            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }

        this.entityManager.getTransaction().commit();
    }
    /*
     * Problem 4: Employees with Salary Over 50 000
     * */

    /*
     * Problem 5: Employees from Department
     * */

    /*
     * Problem 6: Adding a New Address and Updating Employee
     * */
    private void addAddressAndUpdateEmployee() {
        Scanner sc = new Scanner(System.in);
        String lastName = sc.nextLine();

        Address address = new Address();
        address.setText("Vitoshka 15");

        List<Address> addresses = this.entityManager
                                      .createQuery("FROM Address")
                                      .getResultList();
        boolean flag = false;
        for (Address address1 : addresses) {
            if (address1.getText().equals(address.getText())) {
                flag = true;
            }
        }

        if (!flag) {
            this.entityManager.getTransaction().begin();

            Town town = this.entityManager
                            .createQuery("FROM Town WHERE id = 32", Town.class)
                            .getSingleResult();
            address.setTown(town);

            this.entityManager.persist(address);
            this.entityManager.getTransaction().commit();

            this.entityManager.getTransaction().begin();
            Employee employee = this.entityManager
                                    .createQuery("FROM Employee WHERE last_name = :lastName", Employee.class)
                                    .setParameter("lastName", lastName)
                                    .getSingleResult();
            employee.setAddress(address);

            this.entityManager.flush();
            this.entityManager.getTransaction().commit();
        }
    }

    /*
     * Problem 7: Addresses with Employee Count
     * */
    private void addressesWithEmployeeCount() {
        this.entityManager.getTransaction().begin();

        List<Address> addresses = this.entityManager
              .createQuery("SELECT all_fields FROM Address all_fields " +
                               "ORDER BY all_fields.employees.size DESC, all_fields.town.id ASC",
                Address.class)
              .setMaxResults(10)
              .getResultList();

      /*  Query query = this.entityManager.createQuery("SELECT a.text, t.name," +
                                                         "COUNT(e.id) AS e_count FROM Address AS a \n" +
                                                         "JOIN Employee AS e \n" +
                                                         "ON a.id = e.address.id\n" +
                                                         "JOIN Town AS t\n" +
                                                         "ON t.id = a.town.id\n" +
                                                         "GROUP BY a.text\n" +
                                                         "ORDER BY e_count DESC, a.town.id");
        List <Address> addressList = query.getResultList();*/
        for (Address o : addresses) {
            System.out.printf("%s, %s - %d employees%n",
                o.getText(),
                o.getTown().getName(),
                o.getEmployees().size());
        }

        this.entityManager.getTransaction().commit();
    }
    /*
     * Problem 8: Get Employee with Project
     * */

    /*
     * Problem 9: Find Latest 10 Projects
     * */

    /*
     * Problem 10:
     * */

    /*
     * Problem 11:
     * */


    /*
     * Problem 12:
     * */


    /*
     * Problem 13:
     * */
}