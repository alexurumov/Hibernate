import entities.Customer;
import entities.Product;
import entities.Sale;
import entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

public class SalesAppDemo {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("code_first_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Customer customer = new Customer();
//        customer.setName("Name");
//        customer.setEmail("email@gmail.com");
//        customer.setCreditCardNumber("1234");
//        customer.setSales(new HashSet<>());
//
//        Product product = new Product();
//        product.setName("Product");
//        product.setPrice(BigDecimal.TEN);
//        product.setQuantity(4.2);
//        product.setSales(new HashSet<>());
//
//        StoreLocation storeLocation = new StoreLocation();
//        storeLocation.setLocationName("Location Name");
//        storeLocation.setSales(new HashSet<>());
//
//        Sale sale = new Sale();
//        sale.setCustomer(customer);
//        sale.setProduct(product);
//        sale.setStoreLocation(storeLocation);
//        sale.setDate(LocalDate.now());
//
//        inTransaction(entityManager,
//                () -> entityManager.persist(sale)
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
