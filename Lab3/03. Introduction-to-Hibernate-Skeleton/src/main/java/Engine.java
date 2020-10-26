import entities.*;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {
    private final EntityManager em;

    private Scanner in = new Scanner(System.in);

    public Engine(EntityManager em) {
        this.em = em;
    }

    @Override
    public void run() {
//        this.removeObjects();
//        this.containsEmployee();
//        this.employeesWithSalaryOver();
//        this.employeesFromDepartment();
//        this.addAddressAndUpdateEmployee();
//        this.addressesWithCount();
//        this.getEmployeeWithProject();
//        this.findLatestNProjects();
//        this.increaseSalaries();
//        this.removeTowns();
//        this.findEmployeesByFirstName();
//        this.employeesMaximumSalaries();
    }

    // 2. Remove Objects
    private void removeObjects() {

        this.em.getTransaction().begin();

        this.em.createQuery("FROM Town WHERE LENGTH(name) > 5", Town.class)
                .getResultList()
                .forEach(em::remove);

        this.em.getTransaction().commit();

        this.em.getTransaction().begin();

        this.em.createQuery("FROM Town", Town.class)
                .getResultList()
                .forEach(town -> {
                    town.setName(town.getName().toLowerCase());
                });

        this.em.getTransaction().commit();
    }

    // 3. Contains Employee
    private void containsEmployee() {
        System.out.println("Enter full name of employee: ");
        String name = this.in.nextLine();

        this.em.getTransaction().begin();

        try {
            Employee employee = this.em.createQuery("FROM Employee WHERE CONCAT(firstName, ' ', lastName) = :name", Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();

//            Employee employee = (Employee) this.em.createNativeQuery("SELECT * FROM employees WHERE CONCAT(first_name, ' ', last_name) = ?")
//                    .setParameter(1, name)
//                    .getSingleResult();

            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
            this.em.getTransaction().rollback();
        }

        this.em.getTransaction().commit();

    }

    // 4. Employees with Salary Over 50 000 (or other number)
    private void employeesWithSalaryOver() {
        System.out.println("Enter desired salary limit: (50000)");
        BigDecimal salaryLimit = BigDecimal.valueOf(this.in.nextLong());

        this.em.getTransaction().begin();

        this.em.createQuery("FROM Employee WHERE salary > :salaryLimit", Employee.class)
                .setParameter("salaryLimit", salaryLimit)
                .getResultList()
                .forEach(employee -> {
                    System.out.println(employee.getFirstName());
                });

        this.em.getTransaction().commit();
    }

    // 5. Employees from Department
    private void employeesFromDepartment() {
        this.em.getTransaction().begin();

        this.em.createQuery("FROM Employee WHERE department.id = 6 ORDER BY salary, id", Employee.class)
                .getResultList()
                .forEach(employee -> System.out.printf("%s %s from %s - $%.2f\n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment().getName(),
                        employee.getSalary()));

        this.em.getTransaction().commit();
    }

    // 6. Adding a New Address and Updating Employee
    private void addAddressAndUpdateEmployee() {
        String lastName = this.in.nextLine();

        this.em.getTransaction().begin();

        Address address = new Address();
        address.setText("Vitoshka 15");
        Town town = this.em.createQuery("FROM Town WHERE id = 32", Town.class)
                .getSingleResult();
        address.setTown(town);

        this.em.persist(address);

        this.em.getTransaction().commit();

        this.em.getTransaction().begin();

        Employee employee = this.em.createQuery("FROM Employee WHERE lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getSingleResult();

        employee.setAddress(address);

        this.em.getTransaction().begin();
    }

    // 7. Address with Employee Count
    private void addressesWithCount() {

        this.em.getTransaction().begin();

        List <Address> addresses = this.em.createQuery("FROM Address ORDER BY employees.size DESC, town.id ASC", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(address -> System.out.printf("%s, %s - %d employees\n",
                address.getText(),
                address.getTown().getName(),
                address.getEmployees().size()));

        this.em.getTransaction().commit();
    }

    // 8. Get Employee with Project
    private void getEmployeeWithProject() {
        System.out.print("Enter ID of employee: ");
        int desiredId = Integer.parseInt(this.in.nextLine());

        this.em.getTransaction().begin();

        Employee employee = this.em.createQuery("FROM Employee WHERE id = :desiredId", Employee.class)
                .setParameter("desiredId", desiredId)
                .getSingleResult();

        System.out.printf("%s %s - %s\n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        this.em.getTransaction().commit();

        this.em.getTransaction().begin();

        this.em.createNativeQuery("SELECT\n" +
                "    p.name\n" +
                "FROM projects p\n" +
                "LEFT JOIN employees_projects ep\n" +
                "    ON p.project_id = ep.project_id\n" +
                "WHERE ep.employee_id = ?\n" +
                "ORDER BY p.name;")
                .setParameter(1, desiredId)
                .getResultStream()
                .forEach(pName -> System.out.printf("\t%s\n", pName));

        this.em.getTransaction().commit();
    }

    // 9. Find Lateset 10 Projects
    private void findLatestNProjects() {
        this.em.getTransaction().begin();

        List <Project> projects= (List<Project>) this.em.createNativeQuery("SELECT\n" +
                "    *\n" +
                "FROM (SELECT\n" +
                "        *\n" +
                "    FROM projects p\n" +
                "    ORDER BY p.start_date DESC\n" +
                "    LIMIT 10) a\n" +
                "ORDER BY a.name;", Project.class)
                .getResultList();

        for (Project project : projects) {
            System.out.printf("Project name: %s\n" +
                            " \tProject Description: %s\n" +
                            " \tProject Start Date: %s\n" +
                            " \tProject End Date: %s\n",
                    project.getName(),
                    project.getDescription(),
                    project.getStartDate(),
                    project.getEndDate());
        }

        this.em.getTransaction().commit();
    }

    // 10. Increase Salaries
    private void increaseSalaries() {
        this.em.getTransaction().begin();

        this.em.createQuery("FROM Employee WHERE department.id IN (1, 2, 4, 11)", Employee.class)
                .getResultList()
                .forEach(employee -> {
                    employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
                    this.em.persist(employee);
                    System.out.printf("%s %s ($%.2f)\n", employee.getFirstName(), employee.getLastName(), employee.getSalary());
                });

        this.em.getTransaction().commit();
    }

    // 11. Remove Towns
    private void removeTowns() {
        System.out.print("Enter town name: ");
        String town = this.in.nextLine();

        this.em.getTransaction().begin();

        List<Address> addresses = this.em.createQuery("FROM Address WHERE town.name = :town", Address.class)
                .setParameter("town", town)
                .getResultList();

        addresses.forEach(this.em::remove);

        Town forDeletion = this.em.createQuery("FROM Town WHERE name = :name", Town.class)
                .setParameter("name", town)
                .getSingleResult();

        this.em.remove(forDeletion);

        System.out.printf("%d %s in %s deleted\n",
                addresses.size(),
                addresses.size() == 1 ? "address" : "addresses",
                town);

        this.em.getTransaction().commit();
    }

    // 12. Find Employees by First Name
    private void findEmployeesByFirstName() {
        System.out.print("Enter first name pattern: ");
        String pattern = this.in.nextLine();

        this.em.getTransaction().begin();

        this.em.createQuery("FROM Employee WHERE firstName LIKE CONCAT(:pattern, '%')", Employee.class)
                .setParameter("pattern", pattern)
                .getResultList()
                .forEach(employee -> System.out.printf("%s %s - %s - ($%.2f)\n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle(),
                        employee.getSalary()));

        this.em.getTransaction().commit();
    }

    // 13. Employees Maximum Salaries
    private void employeesMaximumSalaries() {
        this.em.getTransaction().begin();

        String query = "SELECT\n" +
                "    d.name,\n" +
                "    MAX(e.salary) AS 'max_salary'\n" +
                "FROM employees e\n" +
                "LEFT JOIN departments d\n" +
                "    ON e.department_id = d.department_id\n" +
                "GROUP BY d.name\n" +
                "HAVING max_salary NOT BETWEEN 30000 AND 70000\n" +
                "ORDER BY d.department_id;";

        List <Object[]> depsWithMaxSalaries = this.em.createNativeQuery(query)
                .getResultList();

        depsWithMaxSalaries.forEach(d -> System.out.printf("%s - %.2f\n", d[0], d[1]));

        this.em.getTransaction().commit();
    }
}
