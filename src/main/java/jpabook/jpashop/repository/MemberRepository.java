package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /**
     * If use @RequiredArgsConstructor, have to add <b>'final'</b> keyword.
     */
    private final EntityManager em;

    /**
     * Member save method.
     * @return Member <B>ID</B>.
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    /**
     * Find member by <b>ID</b>.
     * @return : Member
     */
    public Member findById(Long id) {
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
