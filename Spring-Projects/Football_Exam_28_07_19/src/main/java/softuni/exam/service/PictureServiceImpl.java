package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.bindingdto.xml.PictureRootSeedDto;
import softuni.exam.domain.entities.bindingdto.xml.PictureSeedDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
  private final static String PICTURES_PATH_XML =
      System.getProperty("user.dir") + "/src/main/resources/files/xml/pictures.xml";
  private final PictureRepository pictureRepository;
  private final XmlParser xmlParser;
  private final FileUtil fileUtil;
  private final ModelMapper modelMapper;
  private final ValidatorUtil validator;
  
  @Autowired
  public PictureServiceImpl(PictureRepository pictureRepository,
                            XmlParser xmlParser,
                            FileUtil fileUtil,
                            ModelMapper modelMapper,
                            ValidatorUtil validator) {
    this.pictureRepository = pictureRepository;
    this.xmlParser = xmlParser;
    this.fileUtil = fileUtil;
    this.modelMapper = modelMapper;
    this.validator = validator;
  }
  
  @Override
  public String importPictures() throws JAXBException {
    StringBuilder sb = new StringBuilder();
    PictureRootSeedDto pictureRootSeedDto =
        this.xmlParser.parseXml(PictureRootSeedDto.class, PICTURES_PATH_XML);
    
    for (PictureSeedDto pictureDto : pictureRootSeedDto.getPictures()) {
      if (this.validator.isValid(pictureDto)) {
        System.out.println("Invalid picture!");
        
        this.validator.violations(pictureDto)
            .forEach(violation -> System.out.println(violation.getMessage()));
        
        continue;
      }
      
      Picture picture = this.modelMapper.map(pictureDto, Picture.class);
      this.pictureRepository.saveAndFlush(picture);
      sb.append(String.format(
          "Successfully imported picture - %s",
          picture.getUrl())).append(System.lineSeparator());
    }
    
    return sb.toString().trim();
  }
  
  @Override
  public boolean areImported() {
    return this.pictureRepository.findAll().size() != 0;
  }
  
  @Override
  public String readPicturesXmlFile() throws IOException {
    return this.fileUtil.readFile(PICTURES_PATH_XML);
  }
  
}
