package alararestaurant.models.dto.binding;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import java.math.BigDecimal;

public class ItemImportDto {
    
    @Expose
    private String name;
    
    @Expose
    private BigDecimal price;
    
    @Expose
    private String category;
    
    public ItemImportDto() {
    }
    
    @Length(min = 3, max = 30)
    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}
