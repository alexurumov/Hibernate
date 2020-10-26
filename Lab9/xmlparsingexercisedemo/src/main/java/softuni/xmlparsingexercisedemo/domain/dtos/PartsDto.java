package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsDto implements Serializable {

    @XmlElement(name = "part")
    private List<PartDto> partDtoList;

    public PartsDto() {
        this.partDtoList = new ArrayList<>();
    }

    public List<PartDto> getPartDtoList() {
        return partDtoList;
    }

    public void setPartDtoList(List<PartDto> partDtoList) {
        this.partDtoList = partDtoList;
    }
}
