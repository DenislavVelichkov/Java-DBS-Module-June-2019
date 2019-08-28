package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserBuyerDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    @SerializedName(value = "buyerFirstName")
    private String firstName;


    @Expose
    @SerializedName(value = "buyerLastName")
    private String lastName;

    public UserBuyerDto() {
    }

    public UserBuyerDto(String name, BigDecimal price, String firstName, String lastName) {
        this.name = name;
        this.price = price;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
