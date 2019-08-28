package alararestaurant.models.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {
    /*•	id – integer, Primary Key
•	name – text with min length 3 and max length 30 (required, unique)
•	employees – Collection of type Employee
*/
    private String name;
    private List<Employee> employees;
    
    public Position() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @OneToMany(targetEntity = Employee.class, mappedBy = "position")
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
