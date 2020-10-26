package mostwanted.domain.entities;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "races")
public class Race extends BaseEntity {

    private Integer laps;

    private District district;
    private List<RaceEntry> entries;

    public Race() {
        this.entries = new ArrayList<>();
    }

    // TODO Check default value
    @Column(name = "laps", nullable = false, columnDefinition = "int(11) default '0'")
    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
    public List<RaceEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RaceEntry> entries) {
        this.entries = entries;
    }

}
