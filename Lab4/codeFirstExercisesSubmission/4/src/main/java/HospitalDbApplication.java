import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HospitalDbApplication {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hospital_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Engine engine = new Engine(entityManager);
        engine.run();

    }
}
