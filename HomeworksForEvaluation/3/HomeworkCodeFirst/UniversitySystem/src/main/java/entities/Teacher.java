package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends BasePeople{

    @Column(name = "email")
    private String email;

    @Column(name = "salary_per_hour")
    private Double salaryPerHour;

    @OneToMany(targetEntity = Course.class, mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Course> courses;

    public Teacher() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(Double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
