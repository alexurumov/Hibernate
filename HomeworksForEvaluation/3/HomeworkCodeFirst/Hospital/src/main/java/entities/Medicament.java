package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medicament")
public class Medicament extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "medicament_id", referencedColumnName = "id")
    private List<Patient> patients;

    public Medicament() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
