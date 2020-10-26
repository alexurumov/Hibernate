package softuni.xmlparsingdemo.domain.dtos;

import javax.persistence.Column;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto implements Serializable {

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    @XmlElement(name = "age")
    private Integer age;

    @XmlElement(name = "cars")
    private CarsDto carsDto;

    public UserDto() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public CarsDto getCarsDto() {
        return carsDto;
    }

    public void setCarsDto(CarsDto carsDto) {
        this.carsDto = carsDto;
    }
}
