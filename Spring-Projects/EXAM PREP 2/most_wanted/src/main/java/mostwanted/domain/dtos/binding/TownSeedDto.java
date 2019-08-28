package mostwanted.domain.dtos.binding;


import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class TownSeedDto {

    @Expose
    private String name;

    public TownSeedDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }
}
