package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Locker;

public interface LockerRepository extends JpaRepository<Locker, Long> {

}
