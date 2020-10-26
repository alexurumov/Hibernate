package entities;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class BaseBillingDetail extends BaseEntity {
    private String number;
    private User owner;
    private String type;

    public BaseBillingDetail() {
    }

    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Column(insertable = false, updatable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
