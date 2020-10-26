import entities.Student;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import utils.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        // JPA CONFIG AND USE

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();

        // -> START TRANSACTION AND WRITE CODE BELOW

        em.getTransaction().begin();
        Student student = new Student();
        student.setName("Teo");
        student.setRegistrationDate(new Date());

        // -> END TRANSACTION AND COMMIT

        em.persist(student);
        em.getTransaction().commit();


        // HIBERNATE CONFIG AND USE

//        Session session = HibernateUtils.getSession();
//
//        session.beginTransaction();

        // -> START TRANSACTION AND WRITE CODE BELOW

        // INSERT STUDENT INTO DB
//        Student student = new Student();
//        student.setName("Pesho");
//        student.setRegistrationDate(new Date());
//        session.save(student);

        // GET STUDENT FROM DB
//        Student student = session.get(Student.class, 2);
//        System.out.println(student);

        // GET ALL STUDENTS FROM DB WITH Hibernate Querry Language (HQL)
//        session.createQuery("FROM Student")
//                .list()
//                .forEach(System.out::println);

        // USING CRITERIA BUILDER

//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery criteria = builder.createQuery();
//        Root<Student> r = criteria.from(Student.class);
//        criteria.select(r).where(builder.like(r.get("name"),"P%"));
//        List<Student> studentList = 	session.createQuery(criteria).getResultList();
//        for (Student student : studentList) {
//            System.out.println(student.getName());
//        }


        // END TRANSACTION AND COMMIT

//        session.getTransaction().commit();
//        session.close();

    }
}
