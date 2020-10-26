package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuppliersDto implements Serializable {

    @XmlElement(name = "supplier")
    private List<SupplierDto> supplierDtoList;

    public SuppliersDto() {
        this.supplierDtoList = new ArrayList<>();
    }

    public List<SupplierDto> getSupplierDtoList() {
        return supplierDtoList;
    }

    public void setSupplierDtoList(List<SupplierDto> supplierDtoList) {
        this.supplierDtoList = supplierDtoList;
    }
}
