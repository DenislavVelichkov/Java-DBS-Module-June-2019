package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.entities.bindingdto.xml.TeamRootSeedDto;
import softuni.exam.domain.entities.bindingdto.xml.TeamSeedDto;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {
  private final static String TEAM_PATH_XML =
      System.getProperty("user.dir") + "/src/main/resources/files/xml/teams.xml";
  private final TeamRepository teamRepository;
  private final XmlParser xmlParser;
  private final FileUtil fileUtil;
  private final ModelMapper modelMapper;
  private final ValidatorUtil validator;
  
  @Autowired
  public TeamServiceImpl(TeamRepository teamRepository,
                         XmlParser xmlParser,
                         FileUtil fileUtil,
                         ModelMapper modelMapper,
                         ValidatorUtil validator) {
    this.teamRepository = teamRepository;
    this.xmlParser = xmlParser;
    this.fileUtil = fileUtil;
    this.modelMapper = modelMapper;
    this.validator = validator;
  }
  
  @Override
  public String importTeams() throws JAXBException {
    StringBuilder sb = new StringBuilder();
    TeamRootSeedDto teamRootSeedDto =
        this.xmlParser.parseXml(TeamRootSeedDto.class, TEAM_PATH_XML);
    
    for (TeamSeedDto teamDto : teamRootSeedDto.getTeamSeedDto()) {
      Team team = this.teamRepository.findAllByName(teamDto.getName())
                      .orElse(null);
      if (this.validator.isValid(teamDto)
              || team != null && team.getName().equals(teamDto.getName())) {
        System.out.println("Invalid team!");
        this.validator.violations(teamDto)
            .forEach(violation -> System.out.println(violation.getMessage()));
        
        continue;
      }
      
      team = this.modelMapper.map(teamDto, Team.class);
      this.teamRepository.saveAndFlush(team);
      
      sb.append(String.format("Successfully imported team - %s",
          team.getName()))
          .append(System.lineSeparator());
    }
    
    return sb.toString().trim();
  }
  
  @Override
  public boolean areImported() {
    return teamRepository.findAll().size() != 0;
  }
  
  @Override
  public String readTeamsXmlFile() throws IOException {
    return this.fileUtil.readFile(TEAM_PATH_XML);
  }
}
