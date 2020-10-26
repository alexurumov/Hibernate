package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {

    @Column(name = "date")
    private Date date;

    @Column(name = "comments")
    private String comment;

    @OneToMany
    @JoinColumn(name = "visitation_id", referencedColumnName = "id")
    private List<Patient> patients;

    public Visitation() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
