package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 3, max = 30, message = "Invalid item name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.01", message = "Invalid item price")
    private BigDecimal price;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("--- Item Name: ").append(this.getName()).append(System.lineSeparator())
                .append("--- Item Price: ").append(this.getPrice()).append(System.lineSeparator());


        return sb.toString();
    }
}
