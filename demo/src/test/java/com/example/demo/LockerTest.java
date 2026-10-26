package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Locker;
import com.example.demo.entity.SportsMember;
import com.example.demo.repository.LockerRepository;
import com.example.demo.repository.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Test
    public void insert() {
        Locker locker1 = lockerRepository.save(Locker.builder().name("locker1").build());

        sportsMemberRepository.save(SportsMember.builder().name("홍길동").locker(locker1).build());
    }

    @Test
    public void read() {
        // locker기준
        Locker locker1 = lockerRepository.findById(1L).get();

        SportsMember member1 = sportsMemberRepository.findById(1L).get();

        System.out.println(locker1);
        System.out.println(member1);
    }

    @Transactional
    @Test
    public void read2() {
        // locker기준
        Locker locker1 = lockerRepository.findById(1L).get();

        SportsMember member1 = sportsMemberRepository.findById(1L).get();

        System.out.println(locker1);
        System.out.println(locker1.getSportsMember().getName());
        System.out.println(member1);
        System.out.println(member1.getLocker().getName());
    }

    @Test
    public void read3() {
        // locker기준
        Locker locker1 = lockerRepository.findById(1L).get();

        // SportsMember member1 = sportsMemberRepository.findById(1L).get();

        System.out.println(locker1);
        System.out.println(locker1.getSportsMember().getName());
        // System.out.println(member1);
        // System.out.println(member1.getLocker().getName());
    }

}
