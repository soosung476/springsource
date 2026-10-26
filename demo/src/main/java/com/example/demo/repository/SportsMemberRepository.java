package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SportsMember;

public interface SportsMemberRepository extends JpaRepository<SportsMember, Long> {

}
