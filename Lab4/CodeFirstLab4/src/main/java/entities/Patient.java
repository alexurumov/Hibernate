package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity{
    final static String DATE_FORMAT = "YYYY-MM-DD";
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private Date dateOfBirth;
    private String picture;
    private boolean isInsured;

    public Patient() {
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Column(name = "is_insured")
    public boolean isInsured() {
        return isInsured;
    }

    public void setInsured(boolean insured) {
        isInsured = insured;
    }

    @Override
    public String toString() {
        return "Patient " + getId() + " - [" +
                "First Name: " + getFirstName() +
                ", Last Name: " + getLastName() +
                ", Address: " + getAddress() +
                ", Email: " + getEmail() +
                ", Date of Birth: " + getDateOfBirth() +
                ", Picture: " + getPicture() +
                ", IsInsured: " + isInsured +
                "]";
    }
}
