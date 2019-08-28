package softuni.jsonexer.domain.dto.binding.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserImportRootDto implements Serializable {

    @XmlElement(name = "user")
    private UserImportDto[] userImportDtos;

    public UserImportRootDto() {
    }

    public UserImportDto[] getUserImportDtos() {
        return userImportDtos;
    }

    public void setUserImportDtos(UserImportDto[] userImportDtos) {
        this.userImportDtos = userImportDtos;
    }
}
