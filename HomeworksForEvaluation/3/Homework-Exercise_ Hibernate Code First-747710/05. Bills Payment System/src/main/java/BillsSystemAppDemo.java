import entities.BankAccount;
import entities.CreditCard;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;

public class BillsSystemAppDemo {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("code_first_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        User user1 = new User();
//        user1.setFirstName("Rambo");
//        user1.setLastName("Marbi");
//        user1.setEmail("rambo@abv.bg");
//        user1.setPassword("1234");
//        user1.setBillingDetail(new HashSet<>());
//
//        User user2 = new User();
//        user2.setFirstName("Gergi");
//        user2.setLastName("Mergi");
//        user2.setEmail("gergi@abv.bg");
//        user2.setPassword("132234");
//        user2.setBillingDetail(new HashSet<>());
//
//        BankAccount bankAccount1 = new BankAccount();
//        bankAccount1.setBankName("Bank1");
//        bankAccount1.setSwiftCode("Swift1");
//        bankAccount1.setNumber("431233124ew1");
//        bankAccount1.setOwner(user1);
//
//        BankAccount bankAccount2 = new BankAccount();
//        bankAccount2.setBankName("Bank2");
//        bankAccount2.setSwiftCode("Swift2");
//        bankAccount2.setNumber("54312fs2134ew1");
//        bankAccount2.setOwner(user2);
//
//        CreditCard creditCard1 = new CreditCard();
//        creditCard1.setNumber("34231");
//        creditCard1.setCardType("Card1");
//        creditCard1.setExpirationMonth("11");
//        creditCard1.setExpirationYear("19");
//        creditCard1.setOwner(user1);
//
//        CreditCard creditCard2 = new CreditCard();
//        creditCard2.setNumber("fr32");
//        creditCard2.setCardType("Card2");
//        creditCard2.setExpirationMonth("04");
//        creditCard2.setExpirationYear("20");
//        creditCard2.setOwner(user2);
//
//        user1.getBillingDetail().add(bankAccount1);
//        user2.getBillingDetail().add(bankAccount2);
//        user1.getBillingDetail().add(creditCard1);
//        user2.getBillingDetail().add(creditCard2);
//
//        inTransaction(entityManager, () -> {
//            entityManager.persist(user1);
//            entityManager.persist(user2);
//        });
    }

    private static void inTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }
}

