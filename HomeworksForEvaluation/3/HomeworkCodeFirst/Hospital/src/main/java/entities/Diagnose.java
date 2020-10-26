package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "comments")
    private String comments;

    @OneToMany
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private List<Patient> patients;

    public Diagnose() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
