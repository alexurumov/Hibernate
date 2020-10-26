import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class Engine implements  Runnable{
    private EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
//        this.removeObjects();
//        this.containsEmployee();
//        this.employeesWithSalaryOver50000();
//        this.employeesFromDepartment();
//        this.addingNewAdressAndUpdatingEmployee();
//        this.addressesWithEmployeeCount();
//        this.getEmployeeWithProject();
//        this.findLatest10Projects();
//        this.increaseSalaries();
//        this.removeTowns();
//        this.findEmployeesByFirstName();
        this.employeesMaximumSalaries();

    }

     /*
    Problem 2 Remove Objects
     */

    private void removeObjects() {
        this.entityManager.getTransaction().begin();

        List<Town> townsToBeDetached = this.entityManager
                .createQuery("FROM Town WHERE CHAR_LENGTH(name) > 5", Town.class)
                .getResultList();

        townsToBeDetached.forEach(t -> this.entityManager.detach(t));

        List<Town> attachedTowns = this.entityManager
                .createQuery("FROM Town WHERE CHAR_LENGTH(name) <= 5", Town.class)
                .getResultList();

        attachedTowns.forEach(t -> {
            t.setName(t.getName().toLowerCase());
            this.entityManager.persist(t);
        });

        this.entityManager.flush();

        this.entityManager.getTransaction().commit();
    }

    /*
    Problem 3 Contains Employee
     */
    private void containsEmployee() {
        Scanner scanner=new Scanner(System.in);
        String name=scanner.nextLine();
        this.entityManager.getTransaction().begin();

        try {
            Employee employee=entityManager.createQuery("FROM Employee WHERE CONCAT(firstName,' ',lastName) = :name",Employee.class)
                    .setParameter("name",name).getSingleResult();
            System.out.println("Yes");
        }catch (Exception e){
            System.out.println("No");
        }
        this.entityManager.getTransaction().commit();
    }

    /*
    Problem 4 employeesWithSalaryOver50000
     */
    private void employeesWithSalaryOver50000() {
        this.entityManager.getTransaction().begin();

            List<Employee> employee=entityManager.createQuery("FROM Employee WHERE salary >50000",Employee.class)
                    .getResultList();
            employee.forEach(e-> System.out.println(e.getFirstName()));

        this.entityManager.getTransaction().commit();
    }

    /*
    Problem 5 employeesFromDepartment
     */
    private void employeesFromDepartment() {
        this.entityManager.getTransaction().begin();

        List<Employee> employees=entityManager.createQuery("FROM Employee WHERE department = 6 ORDER BY salary,id", Employee.class).getResultList();

        employees.forEach(e-> System.out.printf("%s %s from %s - %s%n",e.getFirstName(),e.getLastName(),e.getDepartment().getName(),e.getSalary()));


        this.entityManager.getTransaction().commit();
    }


    /*
    Problem 6 addingNewAddressAndUpdatingEmployee
     */

    private void addingNewAdressAndUpdatingEmployee() {
        Scanner scanner=new Scanner(System.in);
        String name=scanner.nextLine();

        entityManager.getTransaction().begin();
        Town town=entityManager.createQuery("FROM Town WHERE id=32",Town.class).getSingleResult();
        Address address=new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);

        entityManager.persist(address);

        Query query=this.entityManager.createQuery("update Employee set address = :address where lastName= :name")
                .setParameter("address",address)
                .setParameter("name",name);

        query.executeUpdate();

        entityManager.getTransaction().commit();
    }


    /*
    Problem 7 addressesWithEmployeeCount
     */
    private void addressesWithEmployeeCount() {
        entityManager.getTransaction().begin();
        List<Address> addresses=entityManager.createQuery("FROM Address ORDER BY size(employees) DESC,town.id")
                .setMaxResults(10).getResultList();

        addresses.forEach(e-> System.out.printf("%s %s, %s - %s employees%n",e.getId(),e.getText(),e.getTown().getName(),e.getEmployees().size()));

        entityManager.getTransaction().commit();
    }


    /*
    Problem 8 getEmployeeWithProject
     */
    private void getEmployeeWithProject() {
        Scanner scanner=new Scanner(System.in);
        int id=Integer.parseInt(scanner.nextLine());

        entityManager.getTransaction().begin();
        Employee employee=entityManager.createQuery("FROM Employee WHERE id= :id",Employee.class).setParameter("id",id).getSingleResult();




        System.out.printf("%s %s - %s%n",employee.getFirstName(),employee.getLastName(),employee.getJobTitle());

        List projectss=entityManager.createNativeQuery("SELECT p.name from employees\n" +
                "join employees_projects e on employees.employee_id = e.employee_id\n" +
                "join projects p on e.project_id = p.project_id\n" +
                "where e.employee_id=?\n" +
                "order by p.name").setParameter(1,id).getResultList();

        for (Object o : projectss) {
            System.out.println(o);
        }
        entityManager.getTransaction().commit();
    }


    /*
    Problem 9 getEmployeeWithProject
     */

    private void findLatest10Projects() {
        entityManager.getTransaction().begin();
        List<Project> project=entityManager.createQuery("FROM Project ORDER BY name",Project.class).getResultList();

        project.forEach(e-> System.out.printf
                ("Project name:%s%n    Project Description:%s%n    Project Start Date:%s%n    Project End Date:%s%n",
                        e.getName(),e.getDescription(),e.getStartDate(),e.getEndDate()));

        entityManager.getTransaction().commit();
    }

    /*
     Problem 10 Increase Salaries
      */
    private void increaseSalaries() {
        entityManager.getTransaction().begin();
        Query query=entityManager.createNativeQuery("UPDATE employees set salary=salary*1.12 WHERE department_id IN (1,2,4,11);");
        query.executeUpdate();

        List<Employee> employees=entityManager.createQuery("FROM Employee WHERE department IN (1,2,4,11)",Employee.class).getResultList();

        employees.forEach(e-> System.out.printf("%s %s ($%s)%n",e.getFirstName(),e.getLastName(),e.getSalary()));
        entityManager.getTransaction().commit();
    }

    /*
       Problem 11 Remove Towns
        */
    private void removeTowns() {
        Scanner scanner=new Scanner(System.in);
        String townName=scanner.nextLine();

        entityManager.getTransaction().begin();
        Town town=entityManager.createQuery("FROM Town WHERE name=: townName",Town.class).setParameter("townName",townName).getSingleResult();
        List<Address> addresses=entityManager.createQuery("FROM Address WHERE town=: town",Address.class).setParameter("town",town).getResultList();
        int count=0;



        for (Address address : addresses) {
            address.setTown(null);
            List<Employee> employee=entityManager.createQuery("FROM Employee where address=: address",Employee.class).setParameter("address",address).getResultList();
            for (Employee employee1 : employee) {
                employee1.setAddress(null);
            }
            entityManager.remove(address);
            count++;
        }

        System.out.printf("%s addresses in %s deleted",count,town.getName());
        entityManager.remove(town);
        entityManager.flush();

        entityManager.getTransaction().commit();
    }


    /*
       Problem 12 Find Employees By First Name
        */

    private void findEmployeesByFirstName() {
        Scanner scanner=new Scanner(System.in);
        String pattern=scanner.nextLine();

        entityManager.getTransaction().begin();
        List<Employee> employees=entityManager.createQuery("FROM Employee WHERE firstName LIKE('SA%')",Employee.class).getResultList();

        employees.forEach(e-> System.out.printf("%s %s - %s - ($%s)%n",e.getFirstName(),e.getLastName(),e.getJobTitle(),e.getSalary()));

        entityManager.getTransaction().commit();
    }


    /*
       Problem 13 Find Employees By First Name
        */

    private void employeesMaximumSalaries() {
        entityManager.getTransaction().begin();
        List<Object[]> result=entityManager.createNativeQuery("SELECT d.name,max(salary) as `max_salary` from employees\n" +
                "join departments d on employees.department_id = d.department_id\n" +
                "group by d.name\n" +
                "having `max_salary` not between 30000 and 70000;").getResultList();

        System.out.println();

        for (Object[] objects : result) {
            System.out.printf("%s - %s%n",objects[0],objects[1]);
        }


        entityManager.getTransaction().commit();
    }
}
