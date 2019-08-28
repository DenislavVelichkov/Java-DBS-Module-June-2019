package softuni.exam.domain.entities.bindingdto.xml;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TeamPictureSeedDto {
    
    @XmlElement(name = "picture")
    private String picture;
    
    public TeamPictureSeedDto() {
    }
    
    @NotNull
    public String getPicture() {
        return picture;
    }
    
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
