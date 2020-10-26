package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemsRootDto {

    @XmlElement(name = "item")
    private List<OrderItemDto> orderItemDtoList;

    public ItemsRootDto() {
    }

    public List<OrderItemDto> getOrderItemDtoList() {
        return orderItemDtoList;
    }

    public void setOrderItemDtoList(List<OrderItemDto> orderItemDtoList) {
        this.orderItemDtoList = orderItemDtoList;
    }
}
