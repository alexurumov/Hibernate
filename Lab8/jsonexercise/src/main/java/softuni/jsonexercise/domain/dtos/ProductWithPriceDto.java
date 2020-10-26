package softuni.jsonexercise.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductWithPriceDto implements Serializable {

    @Expose
    String name;

    @Expose
    BigDecimal price;

    public ProductWithPriceDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
