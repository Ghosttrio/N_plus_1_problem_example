package com.nplusone.repository;

import com.nplusone.domain.Member;
import com.nplusone.domain.Team;
import com.nplusone.respository.MemberRepository;
import com.nplusone.respository.TeamRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class DomainNPlusOneTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EntityManager entityManager;


    @BeforeEach
    @Transactional
    void setup(){
        System.out.println("========================================================================================");
        List<Team> teams = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            teams.add(Team.builder().name("team" + i).build());
        }
        teamRepository.saveAll(teams);

        List<Member> members = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            members.add(Member.builder().name("member" + i).teamId(teams.get(i).getId()).build());
        }
        memberRepository.saveAll(members);

        entityManager.clear();
        System.out.println("========================================================================================");
    }

    @Test
    void 도메인로직테스트() {
        /**
         *  원하는 결과 -> team findAll 쿼리 1번
         *  실제 결과 -> team findAll 쿼리 1번
         *  애초에 Team만으로 Member를 조회할 수 없다.
         *  Join을 이용하자
         */
//        List<Team> teams = teamRepository.findAll();

        teamRepository.findAllWithMember();
    }
}


