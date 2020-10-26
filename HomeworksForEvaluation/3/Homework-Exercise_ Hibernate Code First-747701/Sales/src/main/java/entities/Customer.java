package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    public Customer() {
    }

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @OneToMany(targetEntity = Sale.class, mappedBy = "customer")
    private Set<Sale> sales;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
