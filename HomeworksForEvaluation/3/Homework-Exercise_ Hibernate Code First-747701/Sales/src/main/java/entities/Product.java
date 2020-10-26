package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    public Product() {
    }

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(targetEntity = Sale.class, mappedBy = "product")
    private Set<Sale> sales;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}