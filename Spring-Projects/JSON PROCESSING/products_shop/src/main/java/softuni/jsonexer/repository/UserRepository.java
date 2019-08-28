package softuni.jsonexer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonexer.domain.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
    
    @Query(value =
            "SELECT u FROM User AS u " +
                    "WHERE u.productsSold IS NOT EMPTY " +
                    "ORDER BY u.productsSold.size DESC , u.lastName")
    List<User> findAllByProductsSoldIsNotNullOrderByProductsSoldDescLastName();
}
