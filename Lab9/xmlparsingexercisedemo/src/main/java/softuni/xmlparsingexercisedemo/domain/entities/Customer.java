package softuni.xmlparsingexercisedemo.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "is_young_driver", nullable = false)
    private boolean isYoungDriver;

    @OneToMany(mappedBy = "customer" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
