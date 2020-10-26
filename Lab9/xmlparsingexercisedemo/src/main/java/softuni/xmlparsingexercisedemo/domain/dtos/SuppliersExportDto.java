package softuni.xmlparsingexercisedemo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuppliersExportDto implements Serializable {

    @XmlElement(name = "supplier")
    private List<SupplierExportDto> supplierExportDtos;

    public SuppliersExportDto() {
        this.supplierExportDtos = new ArrayList<>();
    }

    public List<SupplierExportDto> getSupplierExportDtos() {
        return supplierExportDtos;
    }

    public void setSupplierExportDtos(List<SupplierExportDto> supplierExportDtos) {
        this.supplierExportDtos = supplierExportDtos;
    }
}
