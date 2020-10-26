package entities.shampoos;

import entities.ingredients.BasicIngredient;
import entities.labels.BasicLabel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shampoos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "shampoo_type", discriminatorType = DiscriminatorType.STRING)
public class BasicShampoo implements Shampoo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal price;

    private String brand;

    @Enumerated(EnumType.STRING)
    private Size size;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BasicLabel label;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BasicIngredient> ingredients;

    protected BasicShampoo() {
        this.setIngredients(new HashSet<>());
    }

    public BasicShampoo(BigDecimal price, String brand, Size size, BasicLabel classicLabel) {
        this();
        this.price = price;
        this.brand = brand;
        this.size = size;
        this.label = classicLabel;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String gerBrand() {
        return this.brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public BigDecimal gerPrice() {
        return this.price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public Size getSize() {
        return this.size;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public BasicLabel getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(BasicLabel label) {
        this.label = label;
    }

    @Override
    public Set<BasicIngredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public void setIngredients(Set<BasicIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
