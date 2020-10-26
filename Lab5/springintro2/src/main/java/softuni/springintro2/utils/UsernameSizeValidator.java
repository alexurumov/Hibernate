package softuni.springintro2.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameSizeValidator implements ConstraintValidator<UsernameSize, String> {

    private int min;
    private int max;

    @Override
    public void initialize(UsernameSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= min && s.length() <= max;
    }
}
