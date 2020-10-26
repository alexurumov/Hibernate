package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "store_locations")
public class StoreLocation extends BaseEntity {

    public StoreLocation() {
    }

    @Column(name = "location_name")
    private String locationName;

    @OneToMany(targetEntity = Sale.class, mappedBy = "storeLocation")
    private Set<Sale> sales;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}