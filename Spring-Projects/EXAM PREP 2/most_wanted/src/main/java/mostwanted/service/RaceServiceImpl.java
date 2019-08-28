package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.binding.RaceRaceEntrySeedDto;
import mostwanted.domain.dtos.binding.RaceSeedDto;
import mostwanted.domain.dtos.binding.RaceSeedRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaceServiceImpl implements RaceService{
    private final static String RACE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/races.xml";
    private final DistrictRepository districtRepository;
    private final RaceRepository raceRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public RaceServiceImpl(FileUtil fileUtil,
                           DistrictRepository districtRepository,
                           RaceRepository raceRepository,
                           RaceEntryRepository raceEntryRepository,
                           XmlParser xmlParser,
                           ValidationUtil validationUtil,
                           ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.districtRepository = districtRepository;
        this.raceRepository = raceRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.findAll().size() != 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        RaceSeedRootDto raceSeedRootDto =
                this.xmlParser.parseXml(RaceSeedRootDto.class, RACE_PATH);
        for (RaceSeedDto raceDto: raceSeedRootDto.getRaces()) {
            District district = this.districtRepository
                    .findAllByName(raceDto.getDistrictName())
                    .orElse(null);
            if (!this.validationUtil.isValid(raceDto) || district == null) {
                System.out.println(Constants.INCORRECT_DATA_MESSAGE);
                System.out.println();

                continue;
            }

            Race race = this.modelMapper.map(raceDto, Race.class);
            race.setDistrict(district);

            List<RaceEntry> raceEntries = new ArrayList<>();
            for (RaceRaceEntrySeedDto raceEntryDto : raceDto.getEntries().getRaceEntries()) {
                RaceEntry raceEntry = this.raceEntryRepository
                        .findById(raceEntryDto.getId())
                        .orElse(null);
                if (raceEntry == null) {
                    System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                    continue;
                }

                raceEntry.setRace(race);
                raceEntries.add(raceEntry);
            }

            this.raceRepository.saveAndFlush(race);
            this.raceEntryRepository.saveAll(raceEntries);
            sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    race.getClass().getSimpleName(), race.getId().toString()))
            .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
