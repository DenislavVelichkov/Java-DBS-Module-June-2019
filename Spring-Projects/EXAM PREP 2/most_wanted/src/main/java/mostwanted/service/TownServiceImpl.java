package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.binding.TownSeedDto;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {
    private final static String TOWN_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/towns.json";
    private final TownRepository townRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository,
                           RacerRepository racerRepository, FileUtil fileUtil,
                           Gson gson,
                           ValidationUtil validationUtil,
                           ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.findAll().size() != 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWN_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        TownSeedDto[] townSeedDtos =
                this.gson.fromJson(this.readTownsJsonFile(), TownSeedDto[].class);
        for (TownSeedDto townSeedDto : townSeedDtos) {
            if (!this.validationUtil.isValid(townSeedDto)) {
                System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                continue;
            }

            Town town =
                    this.townRepository
                            .findAllByName(townSeedDto.getName())
                            .orElse(null);

            if (town == null) {
                town = this.modelMapper.map(townSeedDto, Town.class);
                this.townRepository.saveAndFlush(town);
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                        town.getClass().getSimpleName(), town.getName()))
                        .append(System.lineSeparator());
            } else {
                System.out.println(Constants.DUPLICATE_DATA_MESSAGE);
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder sb = new StringBuilder();
        List<Racer> racersWithTowns = this.racerRepository.findAll()
                .stream()
                .filter(racer -> racer.getHomeTown() != null)
                .collect(Collectors.toList());

        Map<String, Integer> townsRacers = new LinkedHashMap<>();
        for (Racer racersWithTown : racersWithTowns) {
            townsRacers.putIfAbsent(racersWithTown.getHomeTown().getName(), 0);
            townsRacers.put(racersWithTown.getHomeTown().getName(),
                    townsRacers.get(racersWithTown.getHomeTown().getName()) + 1);

        }

        townsRacers
                .entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    int result = Integer.compare(o2.getValue(), o1.getValue());
                    return result != 0 ?
                            result
                            :
                            o1.getKey().compareTo(o2.getKey());
                })
                .forEach(entry -> {
                    sb.append(String.format("Name: %s", entry.getKey()))
                            .append(System.lineSeparator())
                            .append(String.format("Racers: %d", entry.getValue()))
                            .append(System.lineSeparator())
                            .append(System.lineSeparator())
                            .append(System.lineSeparator());
                });

        return sb.toString().trim();
    }
}
