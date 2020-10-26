package softuni.springintro2.entities;

import net.bytebuddy.implementation.bind.annotation.Default;
import softuni.springintro2.utils.AgeSize;
import softuni.springintro2.utils.Password;
import softuni.springintro2.utils.UsernameSize;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private LocalDateTime registeredOn;
    private LocalDateTime lastTimeLoggedIn;
    private int age;
    private Deleted isDeleted;

    private Town bornTown;
    private Town currentlyLivingTown;

    private String firstName;
    private String lastName;
    private String fullName;

    private Set<User> friends;

    private Picture profilePicture;

    private Set<Album> albums;

    @Column(name = "username", nullable = false)
    @UsernameSize(min = 4, max = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,50}", message = "Invalid password")

    @Column(name = "password", nullable = false)
    @Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false)
    @Email(regexp =
            "([A-Za-z0-9]+)+(([-_.])*([A-Za-z0-9]+)*)*[@](([A-Za-z]+)+(([-])*([A-Za-z]+)*)*)(([.])(([A-Za-z]+)+(([-])*([A-Za-z]+)*)*))+",
            message = "Invalid email address")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "registered_on")
    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Column(name = "last_time_logged_in")
    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    @Column(name = "age")
    @AgeSize(min = 1, max = 120)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "is_deleted")
    public Deleted getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Deleted isDeleted) {
        this.isDeleted = isDeleted;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "born_town_id", referencedColumnName = "id")
    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "currently_living_town_id", referencedColumnName = "id")
    public Town getCurrentlyLivingTown() {
        return currentlyLivingTown;
    }

    public void setCurrentlyLivingTown(Town currentlyLivingTown) {
        this.currentlyLivingTown = currentlyLivingTown;
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

    @Transient
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_friends",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToOne
    @JoinColumn(name = "profile_picture_id", referencedColumnName = "id")
    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    @OneToMany(mappedBy = "owner", targetEntity = Album.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
