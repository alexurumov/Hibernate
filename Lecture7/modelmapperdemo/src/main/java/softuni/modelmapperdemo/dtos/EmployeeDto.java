package softuni.modelmapperdemo.dtos;

import softuni.modelmapperdemo.entities.Employee;

import java.math.BigDecimal;

public class EmployeeDto {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String manager;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f – Manager: %s",
                this.getFirstName(),
                this.getLastName(),
                this.getSalary(),
                this.getManager() == null ? "[no manager]" : this.getManager());
    }
}
