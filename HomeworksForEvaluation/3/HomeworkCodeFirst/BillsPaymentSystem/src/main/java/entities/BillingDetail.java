package entities;


import javax.persistence.*;

@Entity
@Table(name = "billing_details")
public class BillingDetail extends BaseEntity {

    @Column(name = "billing_number")
    private String billingNumber;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    public BillingDetail() {
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(String billingNumber) {
        this.billingNumber = billingNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
