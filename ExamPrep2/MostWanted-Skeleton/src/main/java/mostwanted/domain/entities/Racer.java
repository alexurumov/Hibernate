package mostwanted.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "racers")
public class Racer extends BaseEntity {

    private String name;
    private Integer age;
    private BigDecimal bounty;
    private Town homeTown;

    private List<Car> cars;
    private List<RaceEntry> entries;

    public Racer() {
        this.cars = new ArrayList<>();
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age")
    @Min(value = 1)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "bounty")
    @DecimalMin(value = "0", inclusive = false)
    public BigDecimal getBounty() {
        return bounty;
    }

    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    public Town getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(Town homeTown) {
        this.homeTown = homeTown;
    }

    @OneToMany(mappedBy = "racer", cascade = CascadeType.ALL)
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @OneToMany(mappedBy = "racer", cascade = CascadeType.ALL)
    public List<RaceEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RaceEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(this.getName()).append(System.lineSeparator());

        if (this.getAge() != null) {
            sb.append("Age: ").append(this.getAge()).append(System.lineSeparator());
        }

        sb.append("Cars: ").append(System.lineSeparator());

        this.getCars().forEach(c -> sb.append(c.toString()).append(System.lineSeparator()));

        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
