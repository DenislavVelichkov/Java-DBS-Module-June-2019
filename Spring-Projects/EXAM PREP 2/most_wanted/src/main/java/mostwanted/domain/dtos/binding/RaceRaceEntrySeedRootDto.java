package mostwanted.domain.dtos.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceRaceEntrySeedRootDto {

    @XmlElement(name = "entry")
    private RaceRaceEntrySeedDto[] raceEntries;

    public RaceRaceEntrySeedRootDto() {
    }

    public RaceRaceEntrySeedDto[] getRaceEntries() {
        return raceEntries;
    }

    public void setRaceEntries(RaceRaceEntrySeedDto[] raceEntries) {
        this.raceEntries = raceEntries;
    }
}
