package softuni.xmlparsingexercisedemo.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(name = "make", nullable = false, updatable = false)
    private String make;

    @Column(name = "model", nullable = false, updatable = false)
    private String model;

    @Column(name = "travelled_distance", nullable = false)
    private Long travelledDistance;

    @OneToOne(mappedBy = "car")
    private Sale sale;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "parts_cars",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id")
    )
    private List<Part> parts;

    public Car() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

}
