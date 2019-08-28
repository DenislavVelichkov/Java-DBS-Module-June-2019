package alararestaurant.models.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    /*•	id – integer, Primary Key
•	name – text with min length 3 and max length 30 (required)
•	items – collection of type Item
*/
    private String name;
    private List<Item> items;
    
    public Category() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @OneToMany(targetEntity = Item.class, mappedBy = "category")
    public List<Item> getItems() {
        return items;
    }
    
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
