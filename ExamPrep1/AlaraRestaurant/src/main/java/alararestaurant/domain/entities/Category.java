package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 30, message = "Invalid category name")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Item> items;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Category: ").append(this.getName()).append(System.lineSeparator());
        this.getItems().forEach(i -> sb.append(i.toString()).append(System.lineSeparator()));

        return sb.toString();
    }
}
