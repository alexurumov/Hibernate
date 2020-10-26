package entities.shampoos;

import entities.labels.BasicLabel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "FN")
public class FreshNuke extends BasicShampoo {

    private static final BigDecimal PRICE = new BigDecimal(9.33);
    private static final String BRAND = "Fresh Nuke";
    private static final Size SIZE = Size.LARGE;

    public FreshNuke(BasicLabel classicLabel) {
        super(PRICE, BRAND, SIZE, classicLabel);
    }

    public FreshNuke() {
    }
}
