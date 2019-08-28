package alararestaurant.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {
    /*•	id – integer, Primary Key
•	name – text with min length 3 and max length 30 (required, unique)
•	category – the item’s category (required)
•	price – decimal (non-negative, minimum value: 0.01, required)
•	orderItems – collection of type OrderItem
*/
    
    private String name;
    private Category category;
    private BigDecimal price;
    private List<OrderItem> orderItems;
    
    public Item() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @ManyToOne(targetEntity = Category.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category categories) {
        this.category = categories;
    }
    
    @Column(name = "price", precision = 19, scale = 2)
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    @OneToMany(targetEntity = OrderItem.class, mappedBy = "item")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
