package com.example.demo.repository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.QItem;
import com.example.demo.entity.QOrder;
import com.example.demo.entity.QOrderItem;
import com.example.demo.entity.QShopMember;
import com.example.demo.entity.ShopMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<ShopMember> members() {
        // 전체 멤버에서 "user1" 조회
        QShopMember member = QShopMember.shopMember;
        JPQLQuery<ShopMember> query = from(member);
        query.where(member.name.eq("user1")).orderBy(member.name.desc());
        query.select(member);

        // 결과 추출

        List<ShopMember> members = query.fetch();
        return members;

    }

    @Override
    public List<Item> items() {
        // item 에서 이름이 item1이고, 가격은 200000 이상인 아이템 추출
        QItem item = QItem.item;
        JPQLQuery<Item> query = from(item);
        query.where(item.name.eq("item1").and(item.price.gt(200000)));
        query.select(item);
        List<Item> items = query.fetch();
        return items;
    }

    @Override
    public List<Object[]> joinTest() {
        // 주문 내역, 주문자
        QOrder order = QOrder.order;
        QShopMember member = QShopMember.shopMember;

        // select o1.*, m.*
        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member));
        JPQLQuery<Tuple> tuple = query.select(order, member);

        List<Tuple> result = tuple.fetch();
        // Tuple => Object[]
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Object[]> subQueryTest() {
        QOrder order = QOrder.order;
        QShopMember member = QShopMember.shopMember;
        QOrderItem orderItem = QOrderItem.orderItem;
        // 동일한 주문 아이디 주문한 상품의 총 개수를 확인

        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member)).join(orderItem).on(order.eq(orderItem.order));

        JPQLQuery<Long> orderCnt = JPAExpressions.select(orderItem.order.count()).from(orderItem)
                .where(orderItem.order.eq(order))
                .groupBy(orderItem.order);

        JPQLQuery<Integer> orderSum = JPAExpressions.select(orderItem.orderPrice.sum()).from(orderItem)
                .where(orderItem.order.eq(order))
                .groupBy(orderItem.order);

        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem, orderCnt, orderSum);
        List<Tuple> result = tuple.fetch();
        // Tuple => Object[]
        return result.stream().map(t -> t.toArray()).collect(Collectors.toList());

    }

}
