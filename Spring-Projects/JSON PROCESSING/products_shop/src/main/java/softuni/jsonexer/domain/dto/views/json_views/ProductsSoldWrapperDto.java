package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class ProductsSoldWrapperDto implements Serializable {

    @Expose
    private Integer count;

    @Expose
    private List<ProductSimpleViewDto> products;

    public ProductsSoldWrapperDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductSimpleViewDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSimpleViewDto> products) {
        this.products = products;
    }
}
