package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersExportDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerExportDto> customerExportDtos;

    public CustomersExportDto() {
        this.customerExportDtos = new ArrayList<>();
    }

    public List<CustomerExportDto> getCustomerExportDtos() {
        return customerExportDtos;
    }

    public void setCustomerExportDtos(List<CustomerExportDto> customerExportDtos) {
        this.customerExportDtos = customerExportDtos;
    }
}
