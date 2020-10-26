package alararestaurant.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "customer", nullable = false)
    private String customer;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderType type = OrderType.ForHere;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

        sb.append("Name: ").append(this.getEmployee().getName()).append(System.lineSeparator())
                .append("Orders:").append(System.lineSeparator())
                .append("   Customer: ").append(this.getCustomer()).append(System.lineSeparator())
                .append("   Items:").append(System.lineSeparator());

        for (OrderItem item : orderItems) {
            sb.append("      Name: ").append(item.getItem().getName()).append(System.lineSeparator())
                    .append("      Price: ").append(item.getItem().getPrice()).append(System.lineSeparator())
                    .append("      Quantity: ").append(item.getQuantity()).append(System.lineSeparator());

            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
