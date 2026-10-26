package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "locker")
@Getter
@Entity
@Builder
public class SportsMember {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name")
    private String name;

    // 테이블명_pk명 (default)
    @JoinColumn(name = "locker_id") // 해당 이름으로 FK 컬럼명 지정
    @OneToOne(fetch = FetchType.LAZY)
    private Locker locker;

    // fetch = FetchType.EAGER : 즉시 (left join)
    // fetch = FetchType.Lazy : 게으른 (필요한 시점)

}
