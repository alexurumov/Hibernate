package softuni.workshop.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{

    private String firstName;
    private String lastName;
    private Integer age;

    private Project project;

    public Employee() {
    }

    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 15, message = "Invalid first name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 15, message = "Invalid last name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age", nullable = false)
    @Min(value = 16, message = "Not enough XP")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
