package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.binding.CarSeedDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService {
    private final static String CAR_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/cars.json";
    private final RacerRepository racerRepository;
    private final CarRepository carRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(RacerRepository racerRepository,
                          CarRepository carRepository,
                          TownRepository townRepository,
                          FileUtil fileUtil,
                          Gson gson,
                          ValidationUtil validationUtil,
                          ModelMapper modelMapper) {
        this.racerRepository = racerRepository;
        this.carRepository = carRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.findAll().size() != 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CAR_PATH);
    }

    @Override
    public String importCars(String carsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        CarSeedDto[] carSeedDto =
                this.gson.fromJson(this.readCarsJsonFile(), CarSeedDto[].class);

        for (CarSeedDto carDto : carSeedDto) {
            if (!this.validationUtil.isValid(carDto)) {
                System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                continue;
            }

            Racer racer = this.racerRepository
                    .findAllByName(carDto.getRacerName())
                    .orElse(null);
            if (racer == null) {
                System.out.println(Constants.INCORRECT_DATA_MESSAGE);

                continue;
            }

            Car car = this.modelMapper.map(carDto, Car.class);
            car.setRacer(racer);

            this.carRepository.saveAndFlush(car);
            sb.append(String.format("Successfully imported %s – %s %s @ %d.",
                    car.getClass().getSimpleName(),
                    car.getBrand(),
                    car.getModel(),
                    car.getYearOfProduction()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
