package exjpapractice;

import org.hibernate.engine.spi.PersistenceContext;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");

            em.persist(team);

            Member2 member = new Member2();
            member.setUsername("kim");
            member.setAddress(new Address("city", "street", "zip"));
            member.changeTeam(team);
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("셀러드");
            member.getAddressHistory().add(new AddressEntity("old1", "street", "zzzz"));
            member.getAddressHistory().add(new AddressEntity("old2", "street2", "zzzz2"));
            member.getAddress().setCity("aaa");

            em.persist(member);
            em.flush();
            em.clear();

            Member2 member1 = em.find(Member2.class, member.getId());

            member1.getAddressHistory().remove(new AddressEntity("old1", "street", "zzzz"));
            member1.getAddressHistory().add(new AddressEntity("new", "streetnew", "zzzznew"));
            member.getAddress().setCity("ee");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
