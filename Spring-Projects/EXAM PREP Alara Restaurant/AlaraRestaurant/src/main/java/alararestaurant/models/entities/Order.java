package alararestaurant.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    /*•	id – integer, Primary Key
    •	customer – text (required)
    •	dateTime – date and time of the order (required)
    •	type – OrderType enumeration with possible values: “ForHere, ToGo (default: ForHere)” (required)
    •	employee – The employee who will process the order (required)
    •	orderItems – collection of type OrderItem
    */
    private String customer;
    private LocalDateTime dateTime;
    private OrderType orderType = OrderType.ForHere;
    private Employee employee;
    private List<OrderItem> orderItems;
    
    public Order() {
    }
    
    @Column(name = "customer", columnDefinition = "TEXT")
    public String getCustomer() {
        return customer;
    }
    
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    public OrderType getOrderType() {
        return orderType;
    }
    
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    
    @ManyToOne(targetEntity = Employee.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    @OneToMany(targetEntity = OrderItem.class, mappedBy = "order")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
