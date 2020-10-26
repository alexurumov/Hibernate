package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@SuppressWarnings("ALL")
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(targetEntity = Sales.class, mappedBy = "product")
    private Set<Sales> sales;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Sales> getSales() {
        return sales;
    }

    public void setSales(Set<Sales> sales) {
        this.sales = sales;
    }
}
