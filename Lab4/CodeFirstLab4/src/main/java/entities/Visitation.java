package entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    private Date date;
    private String comments;

    public Visitation() {
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Visitation " + getId() + " - [" +
                "Date: " + getDate() +
                ", Comments: " + getComments() +
                "]";
    }
}
