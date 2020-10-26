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
public class PartsExportDto implements Serializable {

    @XmlElement(name = "part")
    private List<PartExportDto> partExportDtos;

    public PartsExportDto() {
        this.partExportDtos = new ArrayList<>();
    }

    public List<PartExportDto> getPartExportDtos() {
        return partExportDtos;
    }

    public void setPartExportDtos(List<PartExportDto> partExportDtos) {
        this.partExportDtos = partExportDtos;
    }
}
