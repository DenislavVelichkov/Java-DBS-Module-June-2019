package mostwanted.domain.dtos.binding;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class DistrictSeedDto {

    @Expose
    private String name;

    @Expose
    private String townName;

    public DistrictSeedDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
