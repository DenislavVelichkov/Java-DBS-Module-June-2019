package softuni.exam.domain.entities.bindingdto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamRootSeedDto {
  
  @XmlElement(name = "team")
  private TeamSeedDto[] teamSeedDto;
  
  public TeamRootSeedDto() {
  }
  
  public TeamSeedDto[] getTeamSeedDto() {
    return teamSeedDto;
  }
  
  public void setTeamSeedDto(TeamSeedDto[] teamSeedDto) {
    this.teamSeedDto = teamSeedDto;
  }
}
