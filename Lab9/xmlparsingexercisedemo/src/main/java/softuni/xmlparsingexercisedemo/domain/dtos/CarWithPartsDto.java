package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWithPartsDto implements Serializable {

    @XmlAttribute(name = "travelled-distance")
    private Long travelledDistance;

    @XmlAttribute
    private String model;

    @XmlAttribute
    private String make;

    @XmlElement(name = "parts")
    private PartExportMainDto partExportMainDto;

    public CarWithPartsDto() {
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public PartExportMainDto getPartExportMainDto() {
        return partExportMainDto;
    }

    public void setPartExportMainDto(PartExportMainDto partExportMainDto) {
        this.partExportMainDto = partExportMainDto;
    }
}
