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
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            이 시점에서는 단방향 관계만 설정됩니다: Member → Team
            member.setTeam(team);

//            그러나 반대 방향의 관계(Team → Members)는 메모리에서 자동으로 업데이트되지 않습니다.
            em.persist(member);

            em.flush(); // 영속성 컨텍스트를 데이터베이스와 동기화
            em.clear(); // 영속성 컨텍스트를 비워서 DB에서 새로 로드하도록 함

            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember);
            List<Member> members = findMember.getTeam().getMembers();
            System.out.println("members = " + members);
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }


            

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
