import entities.ingredients.AmmoniumChloride;
import entities.ingredients.BasicIngredient;
import entities.ingredients.Mint;
import entities.ingredients.Nettle;
import entities.labels.BasicLabel;
import entities.shampoos.BasicShampoo;
import entities.shampoos.FreshNuke;
import entities.shampoos.PinkPanther;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("shampoo_company");

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        BasicIngredient am = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel("Fresh Nuke Shampoo", "Contains mint and nettle");

        BasicShampoo shampoo1 = new FreshNuke(label);
        BasicShampoo shampoo2 = new PinkPanther(label);

        shampoo1.getIngredients().add(mint);
        shampoo1.getIngredients().add(nettle);
        shampoo1.getIngredients().add(am);
        em.persist(shampoo1);

        shampoo2.getIngredients().add(nettle);
        shampoo2.getIngredients().add(am);

        em.persist(shampoo2);

        em.getTransaction().commit();
        em.close();
    }
}
