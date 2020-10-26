package softuni.springintro2.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeSizeValidator implements ConstraintValidator<AgeSize, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(AgeSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer >= this.min && integer <= this.max;
    }
}
