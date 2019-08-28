package softuni.exam.domain.entities.bindingdto.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class PictureOfPlayerSeedDto {
    
    @Expose
    private String url;
    
    public PictureOfPlayerSeedDto() {
    }
    
    @NotNull
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
