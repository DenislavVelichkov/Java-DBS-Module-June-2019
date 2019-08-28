package softuni.exam.domain.entities.bindingdto.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class TeamOfPlayerSeedDto {
    
    @Expose
    private String name;
    
    @Expose
    private PictureOfPlayerSeedDto picture;
    
    public TeamOfPlayerSeedDto() {
    }
    
    @NotNull
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @NotNull
    public PictureOfPlayerSeedDto getPicture() {
        return picture;
    }
    
    public void setPicture(PictureOfPlayerSeedDto picture) {
        this.picture = picture;
    }
}
