package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ShopMember;

public interface ShopMemberRepository extends JpaRepository<ShopMember, Long> {

}
