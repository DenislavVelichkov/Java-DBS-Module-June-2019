package app.ccb.domain.dtos.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ClientSeedDto implements Serializable {

    @Expose
    @SerializedName(value = "first_name")
    private String firstName;

    @Expose
    @SerializedName(value = "last_name")
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    @SerializedName(value = "appointed_employee")
    private String appointedEmployee;

    public ClientSeedDto() {
    }

    @NotNull(message = "First name required!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "Last name required!")
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

    public String getAppointedEmployee() {
        return appointedEmployee;
    }

    public void setAppointedEmployee(String appointedEmployee) {
        this.appointedEmployee = appointedEmployee;
    }
}
