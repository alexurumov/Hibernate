package softuni.jsonexercise.domain.dtos;

import com.google.gson.annotations.Expose;
import softuni.jsonexercise.domain.entities.Product;

import java.io.Serializable;
import java.util.List;

public class UserSoldProductsDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private List<ProductsSoldDto> soldProducts;

    public UserSoldProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductsSoldDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductsSoldDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
