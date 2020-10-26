package softuni.gamestoredemo2.domain.entities;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    private String title;
    private String trailer;
    private String imageThumbnail;
    private Double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    private Set<User> users;

    public Game() {
        this.users = new HashSet<>();
    }

    @Column(name = "title", nullable = false, unique = true)
    @Pattern(regexp = "[A-Z]\\w{2,100}", message = "Invalid game title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "trailer", unique = true)
    @Size(min = 11, max = 11)
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Column(name = "image_thumbnail", unique = true)
    @Pattern(regexp = "http[s]{0,1}:\\/\\/(.)+", message = "Invalid image thumbnail")
    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    @Column(name = "size", nullable = false, precision = 19, scale = 1)
    @Min(0)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    @Min(0)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "description")
    @Size(min = 20, max = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "release_date", nullable = false)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ManyToMany(targetEntity = User.class, mappedBy = "games", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return title.equals(game.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
