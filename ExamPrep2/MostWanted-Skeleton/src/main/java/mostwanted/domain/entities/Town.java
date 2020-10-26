package mostwanted.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {

    private String name;

    private List<District> districts;

    private List<Racer> racers;

    public Town() {
        this.districts = new ArrayList<>();
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL)
    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    @OneToMany(mappedBy = "homeTown", cascade = CascadeType.ALL)
    public List<Racer> getRacers() {
        return racers;
    }

    public void setRacers(List<Racer> racers) {
        this.racers = racers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(this.getName()).append(System.lineSeparator())
                .append("Racers: ").append(this.getRacers().size()).append(System.lineSeparator());

        return sb.toString();
    }
}
