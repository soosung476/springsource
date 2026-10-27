package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Item;
import com.example.demo.entity.ShopMember;

public interface QueryDslOrderRepository {
    List<ShopMember> members();

    List<Item> items();

    List<Object[]> joinTest();

    List<Object[]> subQueryTest();

}
