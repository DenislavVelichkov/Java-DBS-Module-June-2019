package app.ccb.domain.dtos.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BranchSeedDto implements Serializable {

    @Expose
    private String name;

    public BranchSeedDto() {
    }

    @NotNull(message = "Name cannot be null")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
