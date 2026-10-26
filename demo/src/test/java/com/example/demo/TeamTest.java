package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Team;
import com.example.demo.entity.TeamMember;
import com.example.demo.repository.TeamMemberRepository;
import com.example.demo.repository.TeamRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insert() {
        Team team1 = teamRepository.save(Team.builder().name("team1").build());
        Team team2 = teamRepository.save(Team.builder().name("team2").build());

        teamMemberRepository.save(TeamMember.builder().name("홍길동").team(team1).build());
        teamMemberRepository.save(TeamMember.builder().name("성춘향").team(team2).build());
    }

    @Test
    public void insert2() {
        Team team1 = teamRepository.findById(1L).get();

        teamMemberRepository.save(TeamMember.builder().name("김지호").team(team1).build());
        teamMemberRepository.save(TeamMember.builder().name("성지호").team(team1).build());
    }

    @Test
    public void read1() {
        TeamMember member = teamMemberRepository.findById(1L).get();
        System.out.println(member);

        // team 정보 조회

        System.out.println(member.getTeam().getName());
    }

    // @Transactional
    @Test
    public void read2() {
        // LazyInitializationException
        // @OneToMany : FetchType.Lazy
        Team team = teamRepository.findById(1L).get();
        System.out.println(team);

        // team 정보 조회

    }

    // u
    @Test
    public void update() {
        TeamMember member = teamMemberRepository.findById(4L).get();

        Team team2 = teamRepository.findById(2L).get();
        member.changeTeam(team2);

        teamMemberRepository.save(member);
    }

    @Test
    public void delete() {
        // 탈퇴 (삭제)
        teamMemberRepository.deleteById(3L);
    }

    @Test
    public void delete2() {

        teamRepository.deleteById(2L);
    }
}
