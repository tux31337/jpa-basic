package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Order;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);


            // 저장
            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member);
            em.persist(team);


            em.flush(); // 영속성 컨텍스트를 데이터베이스와 동기화
            em.clear(); // 영속성 컨텍스트를 비워서 DB에서 새로 로드하도록 함

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
