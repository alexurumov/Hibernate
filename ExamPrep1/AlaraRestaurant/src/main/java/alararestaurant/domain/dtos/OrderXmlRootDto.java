package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderXmlRootDto {

    @XmlElement(name = "order")
    private List<OrderXmlDto> orderXmlDtoList;

    public OrderXmlRootDto() {
    }

    public List<OrderXmlDto> getOrderXmlDtoList() {
        return orderXmlDtoList;
    }

    public void setOrderXmlDtoList(List<OrderXmlDto> orderXmlDtoList) {
        this.orderXmlDtoList = orderXmlDtoList;
    }
}
