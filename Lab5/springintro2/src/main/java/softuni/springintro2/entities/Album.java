package softuni.springintro2.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {

    private String name;
    private String backgroundColour;
    private IsPublic isPublic;

    private Set<Picture> pictures;

    private User owner;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "background_colour")
    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "is_public")
    public IsPublic getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(IsPublic isPublic) {
        this.isPublic = isPublic;
    }

    @ManyToMany
    @JoinTable(name = "albums_pictures",
    joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"))
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }


    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
