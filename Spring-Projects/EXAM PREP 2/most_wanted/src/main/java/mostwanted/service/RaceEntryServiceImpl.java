package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.binding.RaceEntrySeedDto;
import mostwanted.domain.dtos.binding.RaceEntrySeedRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {
    private final static String RACE_ENTRY_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";
    private final RacerRepository racerRepository;
    private final CarRepository carRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public RaceEntryServiceImpl(RacerRepository racerRepository, CarRepository carRepository, RaceEntryRepository raceEntryRepository,
                                FileUtil fileUtil,
                                XmlParser xmlParser,
                                ValidationUtil validationUtil,
                                ModelMapper modelMapper) {
        this.racerRepository = racerRepository;
        this.carRepository = carRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.findAll().size() != 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACE_ENTRY_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        RaceEntrySeedRootDto raceRaceEntrySeedRootDto =
                this.xmlParser.parseXml(RaceEntrySeedRootDto.class, RACE_ENTRY_PATH);
        for (RaceEntrySeedDto raceRaceEntrySeedDto : raceRaceEntrySeedRootDto.getRaceEntrySeedDtos()) {
            Racer racer = this.racerRepository
                    .findAllByName(raceRaceEntrySeedDto.getRacer())
                    .orElse(null);
            Car car = this.carRepository
                    .findById(raceRaceEntrySeedDto.getCarId())
                    .orElse(null);

            if (racer == null || car == null) {
                System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                continue;
            }

            RaceEntry raceEntry = this.modelMapper.map(raceRaceEntrySeedDto, RaceEntry.class);
            raceEntry.setRacer(racer);
            raceEntry.setCar(car);
            raceEntry.setRace(null);
            this.raceEntryRepository.saveAndFlush(raceEntry);
            sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    raceEntry.getClass().getSimpleName(), raceEntry.getId().toString()))
                    .append(System.lineSeparator());

        }

        return sb.toString().trim();
    }
}
