package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.demo.entity.Order;

// public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {
public interface OrderRepository extends JpaRepository<Order, Long>, QueryDslOrderRepository {

}
