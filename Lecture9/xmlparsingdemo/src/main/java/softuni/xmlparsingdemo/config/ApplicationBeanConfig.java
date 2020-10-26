package softuni.xmlparsingdemo.config;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import softuni.jsonexercise.util.FileUtil;
//import softuni.jsonexercise.util.ValidationUtil;
//import softuni.jsonexercise.util.impl.FileUtilImpl;
//import softuni.jsonexercise.util.impl.ValidationUtilImpl;

//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;

@Configuration
public class ApplicationBeanConfig {

//    @Bean
//    public Gson gson() {
//        return new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .setPrettyPrinting()
//                .create();
//    }

//    @Bean
//    public FileUtil fileUtil() {
//        return new FileUtilImpl();
//    }

//    @Bean
//    public Validator validator() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        return factory.getValidator();
//
//    }

//    @Bean
//    public ValidationUtil validationUtil() {
//        return new ValidationUtilImpl(validator());
//    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
