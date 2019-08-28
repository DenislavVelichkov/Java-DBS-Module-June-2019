package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class UsersCountDto implements Serializable {

    @Expose
    private Integer usersCount;

    @Expose
    private List<SellerAndSoldProductsDto> users;



    public UsersCountDto() {
    }

    public UsersCountDto(Integer usersCount, List<SellerAndSoldProductsDto> users) {
        this.usersCount = usersCount;
        this.users = users;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<SellerAndSoldProductsDto> getUser() {
        return users;
    }

    public void setUser(List<SellerAndSoldProductsDto> users) {
        this.users = users;
    }
}
