package softuni.jsonexercise.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private int age;

    private Set<Product> productsBought;
    private Set<Product> productsSold;

    private Set<User> friends;

    public User() {
        this.productsBought = new HashSet<>();
        this.productsSold = new HashSet<>();
        this.friends = new HashSet<>();
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

    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @OneToMany(mappedBy = "buyer")
    public Set<Product> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(Set<Product> productsBought) {
        this.productsBought = productsBought;
    }

    @OneToMany(mappedBy = "seller")
    public Set<Product> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(Set<Product> productsSold) {
        this.productsSold = productsSold;
    }

    @ManyToMany
    @JoinTable(
            name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
