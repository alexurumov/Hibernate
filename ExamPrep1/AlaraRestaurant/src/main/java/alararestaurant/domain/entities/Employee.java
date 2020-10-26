package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 30, message = "Invalid employee name")
    private String name;

    @Column(name = "age", nullable = false)
    @Min(value = 15, message = "Invalid Age")
    @Max(value = 80, message = "Invalid Age")
    private Integer age;

    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Order> orders;

    public Employee() {
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
