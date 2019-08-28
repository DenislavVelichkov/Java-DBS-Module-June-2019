package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SellerAndSoldProductsDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    private ProductsSoldWrapperDto soldProducts;

    public SellerAndSoldProductsDto() {
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

    public ProductsSoldWrapperDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductsSoldWrapperDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
