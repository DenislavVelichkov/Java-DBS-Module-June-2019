package alararestaurant.models.dto.binding;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class PositionImportDto {
    
    @Expose
    private String name;
    
    public PositionImportDto() {
    }
    
    @Length(min = 3, max = 30)
    @NotNull
    @Column(unique = true)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
