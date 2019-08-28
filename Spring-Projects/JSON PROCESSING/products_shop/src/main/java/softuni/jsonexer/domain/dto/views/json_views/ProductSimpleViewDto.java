package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductSimpleViewDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    public ProductSimpleViewDto() {
    }

    public ProductSimpleViewDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

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
}
