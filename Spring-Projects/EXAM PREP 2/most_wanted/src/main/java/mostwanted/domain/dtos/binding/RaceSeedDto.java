package mostwanted.domain.dtos.binding;

import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceSeedDto {

    @XmlElement
    private Integer laps;

    @XmlElement(name = "district-name")
    private String districtName;

    @XmlElement(name = "entries")
    private RaceRaceEntrySeedRootDto entries;

    public RaceSeedDto() {
    }

    @NotNull
    @ColumnDefault(value = "0")
    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    @NotNull
    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public RaceRaceEntrySeedRootDto getEntries() {
        return entries;
    }

    public void setEntries(RaceRaceEntrySeedRootDto entries) {
        this.entries = entries;
    }
}
