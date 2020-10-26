package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsDto {

    @XmlElement(name = "car")
    private List<CarDto> carDtoList;

    public CarsDto() {
    }

    public List<CarDto> getCarDtoList() {
        return carDtoList;
    }

    public void setCarDtoList(List<CarDto> carDtoList) {
        this.carDtoList = carDtoList;
    }
}
