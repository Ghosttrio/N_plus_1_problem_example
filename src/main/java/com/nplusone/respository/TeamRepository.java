package com.nplusone.respository;

import com.nplusone.domain.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select t from Team t join fetch t.members")
    List<Team> findAllFetch();

    @EntityGraph(attributePaths = "members")
    @Query("select t from Team t")
    List<Team> findAllEntityGraph();
}
