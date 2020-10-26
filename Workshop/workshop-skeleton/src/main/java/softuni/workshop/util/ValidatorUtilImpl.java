package softuni.workshop.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorUtilImpl implements ValidatorUtil{

    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).size() == 0;
    }

    @Override
    public <E> List<ConstraintViolation<E>> violations(E entity) {

        return new ArrayList<>(validator.validate(entity));

    }
}
