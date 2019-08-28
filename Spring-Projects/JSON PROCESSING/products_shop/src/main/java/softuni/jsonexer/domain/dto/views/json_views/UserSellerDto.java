package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSellerDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    @SerializedName(value = "soldProducts")
    private List<UserBuyerDto> buyers;

    public UserSellerDto() {
        this.buyers = new ArrayList<>();
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

    public List<UserBuyerDto> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<UserBuyerDto> buyers) {
        this.buyers = buyers;
    }
}
