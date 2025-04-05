package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Order order = em.find(Order.class, 1L);
            order.addOrderItem(new OrderItem());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // entityManager가 connection을 갖고 있어서 꼭 닫아줘야ㅕ함.
            em.close();
        }

        emf.close();
    }
}
