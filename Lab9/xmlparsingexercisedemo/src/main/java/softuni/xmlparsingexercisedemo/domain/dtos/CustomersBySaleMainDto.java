package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersBySaleMainDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerBySalesDto> customerBySalesDtos;

    public CustomersBySaleMainDto() {
        this.customerBySalesDtos = new ArrayList<>();
    }

    public List<CustomerBySalesDto> getCustomerBySalesDtos() {
        return customerBySalesDtos;
    }

    public void setCustomerBySalesDtos(List<CustomerBySalesDto> customerBySalesDtos) {
        this.customerBySalesDtos = customerBySalesDtos;
    }
}
