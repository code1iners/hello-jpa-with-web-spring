package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {

        em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status" +
                " and m.name like :name" , Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();

        // note. dynamic query way 1.
//        TypedQuery<Order> query = getDynamicQuery1(orderSearch);

        // note. dynamic query way 2.
        TypedQuery<Order> query = getDynamicQuery2(orderSearch);

        return query.getResultList();
    }

    private TypedQuery<Order> getDynamicQuery1(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";

        boolean isFirstCondition = true;

        // note. order status.
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        // note. member name.
        if (orderSearch.getMemberName() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            }
            jpql += " and";
        }
        jpql += " m.name like :name";

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);


        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query;
    }

    /**
     * <h3>JPA Criteria (Standard).</h3>
     * @return
     */
    public TypedQuery<Order> getDynamicQuery2(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // note. order status.
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // note. member name.
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        return em.createQuery(cq).setMaxResults(1000);
    }

    /**
     * <h3>Query DSL.</h3>
     */
//    public List<Order> getDynamicQuery3(OrderSearch orderSearch) {
//
//    }
}
