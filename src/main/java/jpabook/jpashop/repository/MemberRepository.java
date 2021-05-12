package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    /**
     * Find member by <b>ID</b>.
     * @return : Member
     */
    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    /**
     * Use SQL query by JPQL (== <b>createQuery</b>).
     * @return : Member list
     */
    public List<Member> findAll() {
        return em
                .createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /**
     * Find member by <b>Name</b>.
     * @return : Member
     */
    public List<Member> findByName(String name) {
        return em
                .createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
