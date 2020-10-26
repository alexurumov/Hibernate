package softuni.springintro2.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= 4 &&
                s.length() <= 30 &&
                containsDigit(s) &&
                containsLowercase(s) &&
                containsSpecialSymbol(s);
    }

    private boolean containsSpecialSymbol(String s) {
        Character[] specialSymbols = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '<', '>', '?'};
        List<Character> symbols = Arrays.stream(specialSymbols).collect(Collectors.toList());

        for (int i = 0; i < s.length(); i++) {
            if (symbols.contains(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLowercase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    private boolean containsDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
