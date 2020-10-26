import entities.WizardDeposit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class GringottsAppDemo {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("code_first_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        WizardDeposit wizardDeposit = new WizardDeposit();
//        wizardDeposit.setAge(11);
//        wizardDeposit.setLastName("Last");
//        short magicWandSize = 2;
//        wizardDeposit.setMagicWandSize(magicWandSize);
//
//        try {
//            inTransaction(entityManager,
//                    () -> entityManager.persist(wizardDeposit)
//            );
//        } catch (PersistenceException ex) {
//            System.out.println("Age or magic wand size must be positive number.");
//            entityManager.getTransaction()
//                    .rollback();
//        }
//
//        WizardDeposit wizardDeposit1 = new WizardDeposit();
//        wizardDeposit1.setAge(-1);
//        wizardDeposit1.setLastName("Name");
//
//        try {
//            inTransaction(entityManager,
//                    () -> entityManager.persist(wizardDeposit1)
//            );
//        } catch (PersistenceException ex) {
//            System.out.println("Age or magic wand size must be positive number.");
//            entityManager.getTransaction()
//                    .rollback();
//        }
    }
//
//    private static void inTransaction(EntityManager entityManager, Runnable runnable) {
//        entityManager.getTransaction()
//                .begin();
//        runnable.run();
//        entityManager.getTransaction()
//                .commit();
//    }
}
