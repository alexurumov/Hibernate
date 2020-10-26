package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleMainDto implements Serializable {

    @XmlElement(name = "sale")
    private List<SaleDto> saleDtos;

    public SaleMainDto() {
        this.saleDtos = new ArrayList<>();
    }

    public List<SaleDto> getSaleDtos() {
        return saleDtos;
    }

    public void setSaleDtos(List<SaleDto> saleDtos) {
        this.saleDtos = saleDtos;
    }
}
