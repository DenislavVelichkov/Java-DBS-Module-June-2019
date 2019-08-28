package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.BaseEntity;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.bindingdto.json.PlayerSeedDto;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final static String PLAYERS_PATH_JSON =
        System.getProperty("user.dir") + "/src/main/resources/files/json/players.json";
    private final static BigDecimal SALARY = BigDecimal.valueOf(100000);
    private final static String TEAM_NAME = "North Hub";
    private final PlayerRepository playerRepository;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validator;
    
    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             Gson gson, FileUtil fileUtil,
                             ModelMapper modelMapper,
                             ValidatorUtil validator) {
        this.playerRepository = playerRepository;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }
    
    @Override
    public String importPlayers() throws IOException {
        StringBuilder sb = new StringBuilder();
        PlayerSeedDto[] playerSeedDtos =
            this.gson.fromJson(this.readPlayersJsonFile(), PlayerSeedDto[].class);
        for (PlayerSeedDto playerSeedDto : playerSeedDtos) {
            if (!this.validator.isValid(playerSeedDto)) {
                System.out.println("Invalid player!");
                this.validator.violations(playerSeedDto)
                    .forEach(violation -> System.out.println(violation.getMessage()));
                
                continue;
            }
            
            Player player = this.modelMapper.map(playerSeedDto, Player.class);
            this.playerRepository.saveAndFlush(player);
            sb.append(String.format("Successfully imported player - %s %s",
                player.getFirstName(), player.getLastName()))
                .append(System.lineSeparator());
        }
        
        return sb.toString().trim();
    }
    
    @Override
    public boolean areImported() {
        return this.playerRepository.findAll().size() != 0;
    }
    
    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(PLAYERS_PATH_JSON);
    }
    
    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(SALARY)
            .forEach(player -> {
                sb.append(String.format("Player name: %s %s",
                    player.getFirstName(), player.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("\tNumber: %s", player.getNumber()))
                    .append(System.lineSeparator())
                    .append(String.format("\tSalary: %s", player.getSalary()))
                    .append(System.lineSeparator())
                    .append(String.format("\tTeam: %s", player.getTeam().getName()))
                    .append(System.lineSeparator());
            });
        
        return sb.toString().trim();
    }
    
    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Team: %s", TEAM_NAME)).append(System.lineSeparator());
        
        this.playerRepository
            .findAll()
            .stream()
            .filter(player -> player.getTeam().getName().equals(TEAM_NAME))
            .sorted(Comparator.comparing(BaseEntity::getId))
            .forEach(player -> {
                sb.append(String.format("\tPlayer name: %s %s - %s",
                    player.getFirstName(), player.getLastName(), player.getPosition()))
                    .append(System.lineSeparator())
                    .append(String.format("\tNumber: %d", player.getNumber()))
                    .append(System.lineSeparator());
            });
        
        return sb.toString().trim();
    }
}
