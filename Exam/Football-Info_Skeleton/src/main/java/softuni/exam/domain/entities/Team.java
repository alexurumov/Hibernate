package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    private String name;

    private Picture picture;
    private List<Player> players;

    public Team() {
    }

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "pictire_id", referencedColumnName = "id")
    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
