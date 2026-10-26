package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
// @Setter
@Getter
@Entity
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name")
    private String name;

    // public Team(Long id, String name) {
    // this.id = id;
    // this.name = name;
    // }

    // public void setId(Long id) {
    // this.id = id;
    // }

    // public void setName(String name) {
    // this.name = name;
    // }

    // 팀 멤버를 확인가능한 메소드
    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<TeamMember> teamMember = new ArrayList<>();
    // public void setTeamMember(List<TeamMember> teamMember) {
    // this.teamMember = teamMember;
    // }

}
