package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 3, max = 30, message = "Invalid position name")
    private String name;

    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Position() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
