package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h3>Item service</h3>
 * <ul>
 *  <li>This class works based on read mode.</li>
 *  <li>So, Added <b>'@Transactional'</b> annotation.</li>
 * </ul>
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemRepository itemRepository;

    /**
     * <h3>Item save.</h3>
     * <ul>
     *  <li><b>'@Transactional'</b> is support readOnly = false</li>
     *  <li><b>Why?</b> it's <b>not read method</b>.</li>
     * </ul>
     */
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * <h3>Find all items.</h3>
     * @return - All items as list.
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * <h3>Find just one item.</h3>
     * @return - just one item.
     */
    public Item findItem(Long itemId) {
        return itemRepository.findById(itemId);
    }
}
