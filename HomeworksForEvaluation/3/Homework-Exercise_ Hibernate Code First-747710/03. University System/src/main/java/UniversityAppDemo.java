import entities.Course;
import entities.Student;
import entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

public class UniversityAppDemo {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("code_first_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Teacher teacher = new Teacher();
//        teacher.setFirstName("John");
//        teacher.setLastName("Smith");
//        teacher.setPhoneNumber("089");
//        teacher.setEmail("email@gmail.com");
//        teacher.setSalaryPerHour(BigDecimal.TEN);
//        teacher.setCourses(new HashSet<>());
//
//        Student student = new Student();
//        student.setFirstName("Frank");
//        student.setLastName("Lambert");
//        student.setPhoneNumber("890");
//        student.setAttendance(56);
//        student.setAverageGrade(5.3);
//        student.setCourses(new HashSet<>());
//
//        Course course = new Course();
//        course.setName("Java DB");
//        course.setDescription("Introduction to DB");
//        course.setStartDate(LocalDate.now());
//        course.setEndDate(LocalDate.now());
//        course.setCredits(42);
//        course.setTeacher(teacher);
//        course.setStudents(new HashSet<>());
//        course.getStudents().add(student);
//
//        inTransaction(entityManager,
//                () -> entityManager.persist(course)
//        );
    }

    private static void inTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction()
                .begin();
        runnable.run();
        entityManager.getTransaction()
                .commit();
    }
}
