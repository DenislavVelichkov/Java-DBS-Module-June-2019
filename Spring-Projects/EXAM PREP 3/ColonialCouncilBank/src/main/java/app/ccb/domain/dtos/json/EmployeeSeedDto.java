package app.ccb.domain.dtos.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeSeedDto implements Serializable {

    @Expose
    @SerializedName(value = "full_name")
    private String fullName;

    @Expose
    private BigDecimal salary;

    @Expose
    @SerializedName(value = "started_on")
    private String startedOn;

    @Expose
    @SerializedName(value = "branch_name")
    private String branchName;

    @NotNull(message = "Branch name missing!")
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public EmployeeSeedDto() {
    }

    @NotNull(message = "Required name!")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartedOn() {
        return LocalDate.parse(startedOn);
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = String.valueOf(startedOn);
    }
}
