package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWithPartsMainDto implements Serializable {

    @XmlElement(name = "car")
    private List<CarWithPartsDto> carWithPartsDtos;

    public CarWithPartsMainDto() {
        this.carWithPartsDtos = new ArrayList<>();
    }

    public List<CarWithPartsDto> getCarWithPartsDtos() {
        return carWithPartsDtos;
    }

    public void setCarWithPartsDtos(List<CarWithPartsDto> carWithPartsDtos) {
        this.carWithPartsDtos = carWithPartsDtos;
    }
}
