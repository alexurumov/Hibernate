import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BillsPaymentSystemApplication {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bills_payment_system");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
    }
}
