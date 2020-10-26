package softuni.jsonexercise.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class ProductsSold2Dto implements Serializable {

    @Expose
    private int count;

    @Expose
    private List<ProductWithPriceDto> products;

    public ProductsSold2Dto() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductWithPriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithPriceDto> products) {
        this.products = products;
    }
}
