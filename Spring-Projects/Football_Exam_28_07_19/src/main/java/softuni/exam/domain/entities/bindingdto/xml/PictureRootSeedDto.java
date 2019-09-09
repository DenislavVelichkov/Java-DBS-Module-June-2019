package softuni.exam.domain.entities.bindingdto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureRootSeedDto {
  
  @XmlElement(name = "picture")
  private PictureSeedDto[] pictures;
  
  public PictureRootSeedDto() {
  }
  
  public PictureSeedDto[] getPictures() {
    return pictures;
  }
  
  public void setPictures(PictureSeedDto[] pictures) {
    this.pictures = pictures;
  }
}
