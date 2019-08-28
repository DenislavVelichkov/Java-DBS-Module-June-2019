package app.ccb.repositories;

import app.ccb.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

   Optional<Employee>findByFirstNameAndLastName(String firstName, String lastName);

   @Query(value =
   "SELECT e FROM Employee AS e WHERE e.clients IS NOT EMPTY " +
           "ORDER BY e.clients.size DESC, e.id")
   List<Employee> findAllByClientsNotNull();
}
