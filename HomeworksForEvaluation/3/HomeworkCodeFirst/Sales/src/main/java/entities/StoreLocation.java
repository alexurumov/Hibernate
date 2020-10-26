package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@SuppressWarnings("ALL")
@Entity
@Table(name = "store_location")
public class StoreLocation extends BaseEntity {

    @Column(name = "location_name")
    private String locationName;

    @OneToMany(targetEntity = Sales.class, mappedBy = "storeLocation")
    private Set<Sales> sales;

    public StoreLocation() {
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Set<Sales> getSales() {
        return sales;
    }

    public void setSales(Set<Sales> sales) {
        this.sales = sales;
    }
}
