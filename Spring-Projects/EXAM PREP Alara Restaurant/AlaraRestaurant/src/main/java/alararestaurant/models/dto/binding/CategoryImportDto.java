package alararestaurant.models.dto.binding;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CategoryImportDto {
    
    @Expose
    private String name;
    
    public CategoryImportDto() {
    }
    
    @NotNull
    @Length(min = 3, max = 30)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
