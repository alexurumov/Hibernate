import entities.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;

public class Engine implements Runnable {
    private EntityManager em;
    private BufferedReader br;

    public Engine(String persistenceUnit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        this.em = emf.createEntityManager();
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        this.em.getTransaction().begin();
        try {
//            this.removeObjects();
//            this.containsEmployee();
//            this.employeesWithSalaryOver50000();
//            this.employeesFromDepartment();
//            this.addingANewAddressAndUpdatingEmployee();
//            this.addressesWithEmployeeCount();
//            this.getEmployeeWithProject();
//            this.findLatest10Projects();
//            this.increaseSalaries();
//            this.removeTowns();
//            this.findEmployeesByFirstName();
            this.employeesMaximumSalaries();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.em.getTransaction().commit();
    }

    // 2.Remove Objects
    private void removeObjects() throws IOException {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Town> criteria = builder.createQuery(Town.class);
        Root<Town> r = criteria.from(Town.class);
        criteria.select(r);
        List<Town> towns = this.em.createQuery(criteria).getResultList();
        for (Town t : towns) {
            if (t.getName().length() > 5) {
                this.em.detach(t);
            } else {
                t.setName(t.getName().toLowerCase());
                this.em.persist(t);
            }
        }
    }

    //3.Contains Employee
    private void containsEmployee() throws IOException {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> r = criteria.from(Employee.class);
        String name = this.br.readLine();
        criteria.select(r).where(
                builder.equal(
                        builder.concat(
                                builder.concat(r.get("firstName"), " "), r.get("lastName")),
                        name)
        );

        List<Employee> employees = this.em.createQuery(criteria).getResultList();
        String output = employees.size() > 0 ? "Yes" : "No";

        System.out.println(output);
    }

    //4.Employees with Salary Over 50 000
    private void employeesWithSalaryOver50000() throws IOException {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> r = criteria.from(Employee.class);
        criteria.select(r).where(builder.greaterThan(r.get("salary"), 50000));

        this.em.createQuery(criteria).getResultList()
                .forEach(e -> System.out.println(e.getFirstName()));
    }

    //5.Employees from Department
    private void employeesFromDepartment() throws IOException {
        String query =
                "SELECT * FROM employees e " +
                        "JOIN departments d on e.department_id = d.department_id " +
                        "WHERE d.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.employee_id;";

        List<Employee> employees = this.em.createNativeQuery(query, Employee.class).getResultList();

        employees.forEach(e ->
                System.out.printf("%s %s from %s - $%.2f%n",
                        e.getFirstName(), e.getLastName(),
                        e.getDepartment().getName(), e.getSalary()));
    }

    //6.Adding a New Address and Updating Employee
    private void addingANewAddressAndUpdatingEmployee() throws IOException {
        Address address = null;
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        try {
            address = this.em.createQuery("FROM Address WHERE text = :addressName", Address.class)
                    .setParameter("addressName", "Vitoshka 15")
                    .getSingleResult();
        } catch (NoResultException ex) {
            CriteriaQuery<Town> townCriteria = builder.createQuery(Town.class);
            Root<Town> town = townCriteria.from(Town.class);
            townCriteria.select(town).where(builder.equal(town.get("name"), "Sofia"));

            address = new Address();
            address.setText("Vitoshka 15");
            address.setTown(this.em.createQuery(townCriteria).getSingleResult());
            this.em.persist(address);
        }

        String employeeName = this.br.readLine();

        CriteriaQuery<Employee> employeeCriteria = builder.createQuery(Employee.class);
        Root<Employee> employee = employeeCriteria.from(Employee.class);
        employeeCriteria.select(employee).where(builder.equal(
                employee.get("lastName"), employeeName
        ));

        List<Employee> employees = this.em.createQuery(employeeCriteria).getResultList();

        for (Employee e : employees) {
            e.setAddress(address);
        }

        this.em.flush();
    }

    //7.Addresses with Employee Count
    private void addressesWithEmployeeCount() throws IOException {
        List<Address> addresses = this.em
                .createQuery("FROM Address AS a " +
                        "ORDER BY size(employees) DESC, town.id", Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address a : addresses) {
            System.out.printf("%s, %s - %d employees%n",
                    a.getText(), a.getTown().getName(),
                    a.getEmployees().size());
        }
    }

    //8.Get Employee with Project
    private void getEmployeeWithProject() throws IOException {
        int employeeId = Integer.parseInt(this.br.readLine());
        List<Object[]> employees = this.em.createNativeQuery(
                "SELECT e.first_name, e.last_name, e.job_title, p.name " +
                        "FROM employees e " +
                        "JOIN employees_projects ep ON ep.employee_id = e.employee_id " +
                        "JOIN projects p on ep.project_id = p.project_id " +
                        "WHERE e.employee_id = :employeeId " +
                        "ORDER BY p.name;")
                .setParameter("employeeId", employeeId)
                .getResultList();

        System.out.printf("%s %s - %s%n",
                employees.get(0)[0], employees.get(0)[1], employees.get(0)[2]);
        for (Object[] e : employees) {
            System.out.println("\t" + e[3]);
        }

    }

    //9.Find Latest 10 Projects
    private void findLatest10Projects() throws IOException {
        List<Project> projects = this.em.createQuery(
                "FROM Project ORDER BY startDate DESC", Project.class
        )
                .setMaxResults(10)
                .getResultList();

        projects.stream().sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf(
                        "Project name: %s%n" +
                                " \tProject Description: %s%n" +
                                " \tProject Start Date:%s%n" +
                                " \tProject End Date: %s%n",
                        p.getName(), p.getDescription(), p.getStartDate().toString(),
                        p.getEndDate() == null ? "null" : p.getEndDate().toString()));
    }

    //10.Increase Salaries
    private void increaseSalaries() throws IOException {
        this.em.createNativeQuery(
                "UPDATE employees e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department_id IN (1, 2, 4, 11);")
                .executeUpdate();

        List<Employee> employees = this.em.createNativeQuery(
                "SELECT * " +
                        "FROM employees e " +
                        "WHERE e.department_id IN (1, 2, 4, 11);", Employee.class)
                .getResultList();

        employees.forEach(e ->
                System.out.printf("%s %s ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getSalary()));
    }

    //11.Remove Towns
    private void removeTowns() throws IOException {
        String townName = this.br.readLine();
        int townId = this.em.createQuery(
                "FROM Town WHERE name = :name", Town.class)
                .setParameter("name", townName)
                .getSingleResult()
                .getId();

        List<Address> addresses = this.em.createQuery(
                "FROM Address WHERE town_id = :townId", Address.class)
                .setParameter("townId", townId)
                .getResultList();

        addresses.forEach(a -> {
            this.em.createNativeQuery(
                    "UPDATE employees " +
                            "SET address_id = NULL " +
                            "WHERE address_id = :id")
                    .setParameter("id", a.getId())
                    .executeUpdate();

            this.em.createNativeQuery(
                    "DELETE FROM addresses WHERE address_id = :id")
                    .setParameter("id", a.getId())
                    .executeUpdate();
        });

        this.em.createNativeQuery("DELETE FROM towns WHERE town_id = :id")
                .setParameter("id", townId)
                .executeUpdate();

        System.out.printf("%d %s in %s deleted", addresses.size(),
                addresses.size() > 1 ? "addresses" : "address", townName);
    }

    //12.Find Employees by First Name
    private void findEmployeesByFirstName() throws IOException {
        String pattern = this.br.readLine();
        List<Employee> employees = this.em.createQuery(
                "FROM Employee WHERE firstName LIKE CONCAT(:pattern, '%')", Employee.class)
                .setParameter("pattern", pattern)
                .getResultList();

        employees.forEach(e ->
                System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }

    //13.Employees Maximum Salaries
    private void employeesMaximumSalaries() throws IOException {
        List<Object[]> records = this.em.createNativeQuery(
                "SELECT d.name, MAX(e.salary) salary " +
                        "FROM employees e " +
                        "JOIN departments d ON e.department_id = d.department_id " +
                        "GROUP BY e.department_id " +
                        "HAVING salary NOT BETWEEN 30000 AND 70000;")
                .getResultList();

        records.forEach(r -> System.out.printf("%s - %s%n", r[0], r[1]));
    }
}
