package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class UserWithSoldProductsDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer age;

    private List<ProductSimpleViewDto> productsBought;

    @Expose
    private List<ProductSimpleViewDto> soldProducts;

    public UserWithSoldProductsDto() {
    }

    public UserWithSoldProductsDto(String firstName, String lastName, Integer age, List<ProductSimpleViewDto> productsBought, List<ProductSimpleViewDto> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.productsBought = productsBought;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<ProductSimpleViewDto> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(List<ProductSimpleViewDto> productsBought) {
        this.productsBought = productsBought;
    }

    public List<ProductSimpleViewDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductSimpleViewDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
