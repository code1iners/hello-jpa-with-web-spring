package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * <h3>Item save method.</h3>
     * <ul>
     *  <li><b>'persist'</b> similar create.</li>
     *  <li><b>'merge'</b> similar update.</li>
     * </ul>
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    /**
     * <h3>Find just one item.</h3>
     * @param id - ID of item.
     * @return Item
     */
    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    /**
     * <h3>Find all items.</h3>
     * @return All items as list.
     */
    public List<Item> findAll() {
        return em
                .createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
