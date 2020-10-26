package softuni.workshop.domain.dtos.importDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyDto implements Serializable {

    @XmlAttribute
    private String name;

    public CompanyDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
