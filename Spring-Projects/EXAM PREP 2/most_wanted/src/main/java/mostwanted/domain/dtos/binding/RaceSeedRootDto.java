package mostwanted.domain.dtos.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceSeedRootDto {

    @XmlElement(name = "race")
    private RaceSeedDto[] races;

    public RaceSeedRootDto() {
    }

    public RaceSeedDto[] getRaces() {
        return races;
    }

    public void setRaces(RaceSeedDto[] races) {
        this.races = races;
    }
}
