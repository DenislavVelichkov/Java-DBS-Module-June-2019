package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.binding.RacerSeedDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RacerServiceImpl implements RacerService{
    private final static String RACER_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/racers.json";
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository,
                            TownRepository townRepository,
                            FileUtil fileUtil,
                            Gson gson,
                            ValidationUtil validationUtil,
                            ModelMapper modelMapper) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.findAll().size() != 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACER_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        RacerSeedDto[] racerSeedDto =
                this.gson.fromJson(this.readRacersJsonFile(), RacerSeedDto[].class);

        for (RacerSeedDto racerDto : racerSeedDto) {
            if (!this.validationUtil.isValid(racerDto)) {
                System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                continue;
            }

            Racer racer = this.racerRepository
                    .findAllByName(racerDto.getName())
                    .orElse(null);
            if (racer == null) {
                racer = this.modelMapper.map(racerDto, Racer.class);

                Town town = this.townRepository
                        .findAllByName(racerDto.getHomeTown())
                        .orElse(null);
                if (town == null) {
                    System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                    continue;
                }

                racer.setHomeTown(town);
                this.racerRepository.saveAndFlush(racer);
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                        racer.getClass().getSimpleName(), racer.getName()))
                        .append(System.lineSeparator());
            }else {
                System.out.println(Constants.DUPLICATE_DATA_MESSAGE);
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder sb = new StringBuilder();
        this.racerRepository.findAll()
                .stream()
                .filter(racer -> racer.getAge() != null)
                .sorted((racer1, racer2) -> {
                    int result = Integer.compare(
                            racer2.getCars().size(), racer1.getCars().size());
                    return result != 0 ?
                            result
                            :
                            racer1.getName().compareTo(racer2.getName());
                })
                .forEach(racer -> {
                    sb.append(String.format("Name: %s", racer.getName()))
                            .append(System.lineSeparator())
                            .append("Cars:")
                            .append(System.lineSeparator());
                    racer.getCars().forEach(car -> {
                        sb.append(String.format("%s %s %d",
                                car.getBrand(), car.getModel(), car.getYearOfProduction()))
                                .append(System.lineSeparator());
                    });

                    sb.append(System.lineSeparator());
                });
        return sb.toString().trim();
    }
}
