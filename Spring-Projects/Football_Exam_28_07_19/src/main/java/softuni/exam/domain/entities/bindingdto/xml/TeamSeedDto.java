package softuni.exam.domain.entities.bindingdto.xml;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedDto {
    
    @XmlElement
    private String name;
    
    @XmlElement
    private PictureSeedDto picture;
    
    public TeamSeedDto() {
    }
    
    @NotNull
    @Length(min = 3, max = 20)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public PictureSeedDto getPicture() {
        return picture;
    }
    
    public void setPicture(PictureSeedDto picture) {
        this.picture = picture;
    }
}
