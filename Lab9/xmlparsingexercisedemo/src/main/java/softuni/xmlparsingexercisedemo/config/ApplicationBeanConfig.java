package softuni.xmlparsingexercisedemo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.xmlparsingexercisedemo.util.FileUtil;
import softuni.xmlparsingexercisedemo.util.ValidationUtil;
import softuni.xmlparsingexercisedemo.util.impl.FileUtilImpl;
import softuni.xmlparsingexercisedemo.util.impl.ValidationUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ApplicationBeanConfig {

//    @Bean
//    public Gson gson() {
//        return new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .setPrettyPrinting()
//                .create();
//    }

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();

    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
