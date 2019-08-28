package softuni.jsonexer.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.jsonexer.util.FileUtilImpl;
import softuni.jsonexer.util.ValidatorUtilImpl;
import softuni.jsonexer.util.XmlParser;
import softuni.jsonexer.util.XmlParserImpl;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public FileUtilImpl fileUtilImpl(){
      return  new FileUtilImpl();
    }

    @Bean
    public Validator validator(){
        return  Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtilImpl validatorUtilImpl(){
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
