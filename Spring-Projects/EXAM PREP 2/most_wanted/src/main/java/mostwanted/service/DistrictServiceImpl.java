package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.binding.DistrictSeedDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService{
    private final static String DISTRICT_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/districts.json";
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository,
                               FileUtil fileUtil,
                               Gson gson,
                               ValidationUtil validationUtil,
                               ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.findAll().size() != 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICT_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        DistrictSeedDto[] districtSeedDtos =
                this.gson.fromJson(this.readDistrictsJsonFile(), DistrictSeedDto[].class);
        for (DistrictSeedDto districtSeedDto : districtSeedDtos) {
            if (!this.validationUtil.isValid(districtSeedDto)) {
                System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                continue;
            }

            District district =
                    this.districtRepository
                            .findAllByName(districtSeedDto.getName())
                            .orElse(null);

            if (district == null) {
                district = this.modelMapper.map(districtSeedDto, District.class);
                Town town =
                        this.townRepository
                                .findAllByName(districtSeedDto.getTownName())
                                .orElse(null);
                if (town == null) {
                    System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                    continue;
                }

                district.setTown(town);
                this.districtRepository.saveAndFlush(district);
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                        district.getClass().getSimpleName(), district.getName()))
                        .append(System.lineSeparator());
            } else {
                System.out.println(Constants.DUPLICATE_DATA_MESSAGE);
            }
        }

        return sb.toString().trim();
    }
}
