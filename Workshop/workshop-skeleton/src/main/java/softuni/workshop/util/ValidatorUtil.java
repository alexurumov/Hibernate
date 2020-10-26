package softuni.workshop.util;

import javax.validation.ConstraintViolation;
import java.util.List;

public interface ValidatorUtil {

    <E> boolean isValid(E entity);

    <E> List<ConstraintViolation<E>> violations(E entity);

}
