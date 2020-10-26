package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends BasePeople {

    @Column(name = "average_grade")
    private Double averageGrade;

    @Column(name = "attendance")
    private String attendance;

    @ManyToMany
    @JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> courses;

    public Student() {
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
