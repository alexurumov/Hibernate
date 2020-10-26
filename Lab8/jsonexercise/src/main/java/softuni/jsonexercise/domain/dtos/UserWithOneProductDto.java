package softuni.jsonexercise.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class UserWithOneProductDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;

    @Expose
    private ProductsSold2Dto productsSold;

    public UserWithOneProductDto() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ProductsSold2Dto getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(ProductsSold2Dto productsSold) {
        this.productsSold = productsSold;
    }
}
