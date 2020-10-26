package softuni.modelmapperdemo.dtos;

import java.util.ArrayList;
import java.util.List;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees = new ArrayList<>();
    private int countOfEmployees;

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

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public int getCountOfEmployees() {
        return countOfEmployees;
    }

    public void setCountOfEmployees(int countOfEmployees) {
        this.countOfEmployees = countOfEmployees;
    }

    @Override
    public String toString() {
        String result = String.format("%s %s | Employees: %d\n",
                this.getFirstName(),
                this.getLastName(),
                this.getEmployees().size());
        for (EmployeeDto employee : employees) {
            result += employee.toString() + "\n";
        }

        return result;
    }
}
