package softuni.xmlparsingdemo.domain.dtos;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsDto implements Serializable {

    @XmlElementWrapper(name = "car")
    private List<CarDto> carDtos;

    public CarsDto() {
    }

    public List<CarDto> getCarDtos() {
        return carDtos;
    }

    public void setCarDtos(List<CarDto> carDtos) {
        this.carDtos = carDtos;
    }
}
