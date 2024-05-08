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
import java.util.stream.Collectors;


@SpringBootTest
@Transactional
public class NPlusOneTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup(){
        System.out.println("========================================================================================");
        List<Team> teams = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            teams.add(Team.builder().name("team" + i).build());
        }
        teamRepository.saveAll(teams);

        List<Member> members = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            members.add(Member.builder().name("member" + i).team(teams.get(i)).build());
        }
        memberRepository.saveAll(members);

        entityManager.clear();
        System.out.println("========================================================================================");
    }

//    @Test
    void NPlusOne테스트() {
        /**
         *  원하는 결과 -> team findAll 쿼리 1번
         *  실제 결과 -> team findAll 쿼리 1번 team에 해당하는 member 쿼리 10번
         */
        teamRepository.findAll();
    }

//    @Test
    void Fetch를_Eager에서_Lazy로변환(){
        /**
         *  원하는 결과 -> team findAll 쿼리 1번
         *  실제 결과 -> team findAll 쿼리 1번 
         *  해결된 것일까? -> 아님
         */
//        teamRepository.findAll();
        /**
         * team의 member를 조회할 때 다시 n+1 문제 발생
         * 원하는 결과 -> team findAll 쿼리 1번
         * 실제 결과 -> team findAll 쿼리 1번 team에 해당하는 member 쿼리 10번
         */
        List<Team> teams = teamRepository.findAll();
        teams.forEach(t -> t.getMembers().forEach(m -> m.getName()));
    }

//    @Test
    void 해결방안1_fetch_join(){
        /**
         *  원하는 결과 -> team findAll 쿼리 1번
         *  실제 결과 -> team findAll 쿼리 1번
         */
        List<Team> teams = teamRepository.findAllFetch();
        teams.forEach(t -> t.getMembers().forEach(m -> m.getName()));
    }

//    @Test
    void 해결방안2_Entity_Graph(){
        /**
         *  원하는 결과 -> team findAll 쿼리 1번
         *  실제 결과 -> team findAll 쿼리 1번
         */
        List<Team> teams = teamRepository.findAllEntityGraph();
        teams.forEach(t -> t.getMembers().forEach(m -> m.getName()));
    }

    @Test
    void 해결방안3_FetchMode_SUBSELECT(){
        /**
         *  원하는 결과 -> team findAll 쿼리 1번
         *  실제 결과 -> team findAll 쿼리 1번
         */
        List<Team> teams = teamRepository.findAll();
        teams.forEach(t -> t.getMembers().forEach(m -> m.getName()));
    }

    @Test
    void 해결방안4_Batch_Size(){
        /**
         *  원하는 결과 -> team findAll 쿼리 1번
         *  실제 결과 -> team findAll 쿼리 1번
         */
        List<Team> teams = teamRepository.findAll();
        teams.forEach(t -> t.getMembers().forEach(m -> m.getName()));
    }
}
