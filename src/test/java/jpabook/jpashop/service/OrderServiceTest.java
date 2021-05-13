package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("book1", 10000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order foundOrder = orderRepository.findById(orderId);
        assertEquals("Order status is ORDER when order the product.", OrderStatus.ORDER, foundOrder.getStatus());
        assertEquals("The number of product types ordered must be correct.", 1, foundOrder.getOrderItems().size());
        assertEquals("The price must be price * count.", 10000 * orderCount, foundOrder.getTotalPrice());
        assertEquals("Stock quantity must be decrease As much ordered.", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Book item = createBook("book1", 10000, 10);

        int orderCount = 11;

        orderService.order(member.getId(), item.getId(), orderCount);
        // when
        
        // then
        fail("Must be raise NotEnoughStockException.");
    }
    

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Book item = createBook("book1", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancel(orderId);

        // then
        Order foundOrder = orderRepository.findById(orderId);

        assertEquals("Order status is ORDER when order the product.", OrderStatus.CANCEL, foundOrder.getStatus());
        assertEquals("Stock quantity must be increase As much ordered.", 10, item.getStockQuantity());
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("city1", "street1", "zipcode1"));
        em.persist(member);
        return member;
    }

}