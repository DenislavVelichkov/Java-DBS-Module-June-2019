package alararestaurant.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{
    /*•	id – integer, Primary Key
•	name – text with min length 3 and max length 30 (required)
•	age – integer in the range [15, 80] (required)
•	position – the employee’s position (required)
•	orders – the orders the employee has processed
*/
    private String name;
    private Integer age;
    private Position position;
    private List<Order> orders;
    
    public Employee() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    @OneToMany(targetEntity = Order.class, mappedBy = "employee")
    public List<Order> getOrders() {
        return orders;
    }
    
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}


