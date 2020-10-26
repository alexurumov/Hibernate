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
public class CarsExportDto implements Serializable {

    @XmlElement(name = "car")
    private List<CarExportDto> carExportDtos;

    public CarsExportDto() {
        this.carExportDtos = new ArrayList<>();
    }

    public List<CarExportDto> getCarExportDtos() {
        return carExportDtos;
    }

    public void setCarExportDtos(List<CarExportDto> carExportDtos) {
        this.carExportDtos = carExportDtos;
    }
}
