package com.nplusone.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "member_name")
    private String name;

    /**
     * 기본 연관관계
     */
//    @ManyToOne
//    private Team team;
//
//    @Builder
//    public Member(String name, Team team) {
//        this.name = name;
//        this.team = team;
//    }

    /**
     * 도메인 로직
     */
    private Long teamId;

    @Builder
    public Member(String name, Long teamId) {
        this.name = name;
        this.teamId = teamId;
    }
}
