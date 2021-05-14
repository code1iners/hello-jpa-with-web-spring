package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * <h3>Service logics.</h3>
     * <ul>
     *  <li>Order.</li>
     *  <li>Cancel.</li>
     *  <li>Retrieve.</li>
     * </ul>
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // note. get entities.
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        // note. create delivery information.
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // note. create order item.
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // note. create order.
        Order order = Order.createOrder(member, delivery, orderItem);

        // note. save order.
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancel(Long orderId) {
        // note. get order entity.
        Order order = orderRepository.findById(orderId);

        // note. cancel order.
        order.cancel();
    }

    public List<Order> findOrder(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
