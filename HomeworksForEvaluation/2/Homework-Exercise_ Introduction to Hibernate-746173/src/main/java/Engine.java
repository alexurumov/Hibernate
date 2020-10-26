import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {
    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
//        this.removeObject();

//        try {
//            this.containsEmployee();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        this.salaryOverFiftyThousand();

//        this.employeesFromDepartment();

//        this.addingAddressUpdateEmployee();

//        this.addressesWithEmployeeCount();

//        this.getEmployeeWithProject();

//        this.latestProjects();

//        this.increaseSalaries();

//        this.removeTowns();

//        this.findEmployeeByFirstName();

//        this.employeeMaxSalary();
    }

    private void removeObject() {
        this.entityManager.getTransaction().begin();
        List<Integer> townList = this.entityManager.createNativeQuery("SELECT town_id FROM towns WHERE LENGTH(name) <= 5").getResultList();
        Query query = this.entityManager.createNativeQuery("UPDATE towns SET name = LOWER(name) WHERE town_id IN (?1)");
        query.setParameter(1, townList);
        query.executeUpdate();
        this.entityManager.getTransaction().commit();
    }

    private void containsEmployee() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        this.entityManager.getTransaction().begin();
        try {
            Object employee = this.entityManager.createNativeQuery("SELECT * FROM employees WHERE CONCAT(first_name, ' ', last_name) = ?1")
                    .setParameter(1, name).getSingleResult();
            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }

        this.entityManager.getTransaction().commit();
    }

    private void salaryOverFiftyThousand() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("FROM Employee WHERE salary > 50000");
        List<Employee> employeesWithSalaryOverFT = query.getResultList();
        for (Employee e : employeesWithSalaryOverFT) {
            System.out.println(e.getFirstName());
        }

        this.entityManager.getTransaction().commit();
    }

    private void employeesFromDepartment() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createNativeQuery("SELECT e.first_name, e.last_name, e.salary FROM employees AS e " +
                "JOIN departments AS d ON e.department_id = d.department_id WHERE d.name = 'Research and Development' " +
                "ORDER BY e.salary, e.employee_id");
        List<Object[]> employees = query.getResultList();
        for (Object[] obj : employees) {
            System.out.printf("%s %s from Research and Development - $%s%n", obj[0], obj[1], obj[2]);
        }

        this.entityManager.getTransaction().commit();
    }

    private void addingAddressUpdateEmployee() {
        Scanner scanner = new Scanner(System.in);
        String lastName = scanner.nextLine();
        this.entityManager.getTransaction().begin();
        Query queryInsert = this.entityManager.createNativeQuery("INSERT INTO addresses(address_text, town_id) VALUES ('Vitoshka 15'," +
                "(SELECT town_id FROM towns WHERE name = 'Sofia'))");
        queryInsert.executeUpdate();
        Query queryUpdate = this.entityManager.createNativeQuery("UPDATE employees SET address_id = " +
                "(SELECT address_id FROM addresses WHERE address_text = 'Vitoshka 15' LIMIT 1) WHERE last_name = ?1").setParameter(1, lastName);
        queryUpdate.executeUpdate();
        this.entityManager.getTransaction().commit();
    }

    private void addressesWithEmployeeCount() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createNativeQuery("SELECT a.address_text, t.name, (SELECT COUNT(e.employee_id)) FROM addresses AS a " +
                "JOIN towns AS t ON a.town_id = t.town_id JOIN employees AS e ON a.address_id = e.address_id GROUP  BY a.address_text " +
                "ORDER BY COUNT(e.employee_id) DESC, t.town_id LIMIT 10");
        List<Object[]> result = query.getResultList();
        for (Object[] objects : result) {
            System.out.printf("%s, %s - %s employees%n", objects[0], objects[1], objects[2]);
        }

        this.entityManager.getTransaction().commit();
    }

    private void getEmployeeWithProject() {
        Scanner scanner = new Scanner(System.in);
        int employeeId = Integer.parseInt(scanner.nextLine());
        this.entityManager.getTransaction().begin();
        Query employeeQuery = this.entityManager.createNativeQuery("SELECT first_name, last_name, job_title FROM employees WHERE employee_id = ?1");
        employeeQuery.setParameter(1, employeeId);
        List<Object[]> employeeData = employeeQuery.getResultList();
        Query projectsQuery = this.entityManager.createNativeQuery("SELECT p.name FROM projects AS p JOIN employees_projects AS ep ON p.project_id = ep.project_id " +
                "JOIN employees AS e ON ep.employee_id = e.employee_id WHERE e.employee_id = ?1 ORDER BY p.name");
        projectsQuery.setParameter(1, employeeId);
        List<String> projectsData = projectsQuery.getResultList();
        System.out.printf("%s %s - %s%n", employeeData.get(0)[0], employeeData.get(0)[1], employeeData.get(0)[2]);
        for (String projectsDatum : projectsData) {
            System.out.printf("%s%n", projectsDatum);
        }

        this.entityManager.getTransaction().commit();
    }

    private void latestProjects() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createNativeQuery("SELECT name, description, start_date, end_date FROM projects ORDER BY start_date DESC, name LIMIT 10");
        List<Object[]> resultData = query.getResultList();
        for (Object[] resultDatum : resultData) {
            System.out.printf("Project name: %s%n", resultDatum[0]);
            System.out.printf("        Project Description: %s%n", resultDatum[1]);
            System.out.printf("        Project Start Date:%s%n", resultDatum[2]);
            System.out.printf("        Project End Date: %s%n", resultDatum[3]);
        }

        this.entityManager.getTransaction().commit();
    }

    private void increaseSalaries() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createNativeQuery("SELECT e.* FROM employees AS e " +
                "JOIN departments AS d ON e.department_id = d.department_id " +
                "WHERE d.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class);
        List<Employee> selectedEmployees = query.getResultList();

        selectedEmployees.forEach(e -> e.setSalary(e.getSalary().multiply(new BigDecimal(1.12))));
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void removeTowns() {
        this.entityManager.getTransaction().begin();
        Scanner scanner = new Scanner(System.in);
        String townName = scanner.nextLine();
        Query townQuery = this.entityManager.createQuery("FROM Town AS t WHERE t.name = ?1")
                .setParameter(1, townName);
        try {
            Town town = (Town) townQuery.getSingleResult();
            Query addressesQuery = this.entityManager.createNativeQuery("SELECT a.* FROM addresses AS a " +
                    "JOIN towns AS t ON a.town_id = t.town_id WHERE t.name = ?1", Address.class);
            addressesQuery.setParameter(1, townName);
            List<Address> addresses = addressesQuery.getResultList();
            Query employeeQuery = this.entityManager.createNativeQuery("UPDATE employees e " +
                    "JOIN addresses a ON e.address_id = a.address_id SET e.address_id = null WHERE e.address_id = ?1");
            for (Address address : addresses) {
                employeeQuery.setParameter(1, address.getId());
                employeeQuery.executeUpdate();
                this.entityManager.remove(address);
            }

            this.entityManager.remove(town);

            this.entityManager.getTransaction().commit();
            if (addresses.size() == 0) {
                System.out.println("No addresses in " + townName + " were deleted.");
            } else if (addresses.size() == 1) {
                System.out.printf("%d address in %s deleted%n", addresses.size(), townName);
            } else {
                System.out.printf("%d addresses in %s deleted%n", addresses.size(), townName);
            }
        } catch (Exception e) {
            System.out.printf("No such town in database%n");
            return;
        }
    }

    private void findEmployeeByFirstName() {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createNativeQuery("SELECT e.* FROM employees AS e " +
                "WHERE e.first_name LIKE CONCAT(?1, '%')", Employee.class);
        query.setParameter(1, pattern);
        List<Employee> employees = query.getResultList();
        for (Employee employee : employees) {
            System.out.printf("%s %s - %s - ($%s)%n",
                    employee.getFirstName(), employee.getLastName(), employee.getJobTitle(), employee.getSalary());
        }

        this.entityManager.getTransaction().commit();
    }

    private void employeeMaxSalary() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createNativeQuery("SELECT MAX(e.salary), d.name FROM employees AS e " +
                "JOIN departments AS d ON e.department_id = d.department_id " +
                "WHERE e.salary NOT BETWEEN 30000 AND 70000 GROUP BY e.department_id");
        List<Object[]> resultData = query.getResultList();
        for (Object[] resultDatum : resultData) {
            System.out.printf("%s - %s%n", resultDatum[1], resultDatum[0]);
        }

        this.entityManager.getTransaction().commit();
    }
}
