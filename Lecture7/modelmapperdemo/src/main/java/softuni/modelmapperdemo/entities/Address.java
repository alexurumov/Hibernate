package softuni.modelmapperdemo.entities;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    private int id;

    private String city;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
