package mostwanted.domain.dtos.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntrySeedRootDto {

    @XmlElement(name = "race-entry")
    private RaceEntrySeedDto[] raceEntrySeedDtos;

    public RaceEntrySeedRootDto() {
    }

    public RaceEntrySeedDto[] getRaceEntrySeedDtos() {
        return raceEntrySeedDtos;
    }

    public void setRaceEntrySeedDtos(RaceEntrySeedDto[] raceEntrySeedDtos) {
        this.raceEntrySeedDtos = raceEntrySeedDtos;
    }
}
