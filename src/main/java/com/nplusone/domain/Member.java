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

    @ManyToOne
    private Team team;

    @Builder
    public Member(String name, Team team) {
        this.name = name;
        this.team = team;
    }
}
