package com.example.demo;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.QItem;
import com.example.demo.entity.QOrder;
import com.example.demo.entity.ShopMember;
import com.example.demo.entity.constants.OrderStatus;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShopMemberRepository;

@Transactional // dirty check =>
@SpringBootTest
public class OrderTest {

    @Autowired
    private ShopMemberRepository shopMemberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testMemberInsert() {
        IntStream.range(1, 6).forEach(i -> {
            ShopMember member = ShopMember.builder()
                    .name("user" + i)
                    .city("서울")
                    .build();
            shopMemberRepository.save(member);
        });

    }

    @Test
    public void testItemInsert() {
        IntStream.range(1, 6).forEach(i -> {
            Item item = Item.builder()
                    .name("item" + i)
                    .price(i * 20000)
                    .stockQuantity(i * 5)
                    .build();
            itemRepository.save(item);
        });

    }

    @Test
    public void testOrderInsert() {

        // 1번 유저가 3번 제품 주문하다.
        ShopMember member = shopMemberRepository.findById(1L).get();
        Item item = itemRepository.findById((3L)).get();

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .orderPrice(item.getPrice())
                .count(1)
                .build();
        orderItemRepository.save((orderItem));

    }

    @Test
    public void testCascadeInsert() {

        // 1번 유저가 3번 제품 주문하다.
        ShopMember member = shopMemberRepository.findById(1L).get();
        Item item = itemRepository.findById((3L)).get();

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .orderPrice(item.getPrice())
                .count(1)
                .build();

        order.getOrderItems().add(orderItem);

        orderRepository.save(order);

        // orderItemRepository.save((orderItem));

    }

    @Transactional(readOnly = true)
    @Test
    public void testList() {

        // 주문내역 조회
        Order order = orderRepository.findById(1L).get();

        System.out.println(order); // Order(id=1, orderStatus=ORDER)

        // 주문상품 조회

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            System.out.println(orderItem); // OrderItem(id=1, orderPrice=60000, count=1)
        });

        // 주문자 조회
        System.out.println(order.getMember()); // ShopMember(id=1, name=user1, city=서울)
    }

    @Commit
    @Test
    public void testUpdate() {

        Item item = itemRepository.findById(2L).get();
        item.changeStockQuantity(150);

        OrderItem orderItem = orderItemRepository.findById(1L).get();
        orderItem.changeOrderPrice((28000));

        // itemRepository.save(item);
        // orderItemRepository.save(orderItem);
    }

    @Commit
    @Test
    public void testDelete() {
        // order_item 먼저 삭제 후 order 삭제

        // order 삭제 시 order_item 도 알아서 삭제
        orderRepository.deleteById(2L);
    }

    @Commit
    @Test
    public void testDelete2() {
        // orphanRemoval = true
        Order order = orderRepository.findById(1L).get();
        order.getOrderItems().remove(0);
    }

    @Test
    public void querydslTest() {
        QItem item = QItem.item;
        QOrder order = QOrder.order;

        // 제품명 item1 을 조회
        System.out.println(itemRepository.findAll(item.name.eq("item1")));

        // 제품명 item으로 시작하는 제품 조회
        System.out.println(itemRepository.findAll(item.name.startsWith("item")));

        // 제품명 item으로 끝나는 제품 조회
        System.out.println(itemRepository.findAll(item.name.endsWith("item")));

        // 제품명 item1이고, 가격이 20000 초과인 제품 조회
        System.out.println(itemRepository.findAll(item.name.eq("item1").and(item.price.gt(20000))));

        // 주문자가 user1 이고, 현재 상태가 ORDER인 제품
        System.out.println(orderRepository.findAll(
                order.member.eq(ShopMember.builder().id(1L).build()).and(order.orderStatus.eq(OrderStatus.ORDER))));

    }
}
