package entities;

import entities.BaseEntity;
import entities.Customer;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    private Product product;
    private Customer customer;
    private StoreLocation storeLocation;
    Date date;

    public Sale() {
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "store_location_id", referencedColumnName = "id")
    public StoreLocation getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
