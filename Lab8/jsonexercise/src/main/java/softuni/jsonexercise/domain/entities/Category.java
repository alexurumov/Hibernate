package softuni.jsonexercise.domain.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;

    private List<Product> products = new ArrayList<>();

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(targetEntity = Product.class, mappedBy = "categories")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
