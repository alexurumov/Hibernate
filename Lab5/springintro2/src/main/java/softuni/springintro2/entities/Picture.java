package softuni.springintro2.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    private String title;
    private String caption;
    private String path;

    private Set<Album> albums;

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "caption")
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToMany(mappedBy = "pictures", targetEntity = Album.class)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
