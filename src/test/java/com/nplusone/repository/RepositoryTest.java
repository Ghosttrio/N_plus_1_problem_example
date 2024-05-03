package com.nplusone.repository;

import com.nplusone.domain.Post;
import com.nplusone.respository.CommentRepository;
import com.nplusone.respository.PostRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.String.format;

@SpringBootTest
@Transactional
public class RepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("N + 1 발생 테스트")
    void test() {
        saveSampleData(); // 10개의 post와, 각각의 post마다 3개씩 댓글 저장
        em.flush();
        em.clear();
        System.out.println("------------ 영속성 컨텍스트 비우기 -----------\n\n");



        System.out.println("------------ POST 전체 조회 요청 ------------");
        List<Post> posts = postRepository.findAll();
        System.out.println("------------ POST 전체 조회 완료. [1번의 쿼리 발생]------------\n\n");




        System.out.println("------------ POST 제목 & 내용 조회 요청 ------------");
        posts.forEach(it -> System.out.println("POST 제목: [%s], POST 내용: [%s]".formatted(it.getTitle(), it.getContent())));
        System.out.println("------------ POST 제목 & 내용 조회 완료. [추가적인 쿼리 발생하지 않음]------------\n\n");




        System.out.println("------------ POST에 달린 comment 내용 조회 요청 [조회된 POST의 개수(N=10) 만큼 추가적인 쿼리 발생]------------");
        posts.forEach(post -> {
            post.getComments().forEach(comment -> {
                System.out.println("POST 제목: [%s], COMMENT 내용: [%s]".formatted(comment.getPost().getTitle(), comment.getContent()));
            });
        });
        System.out.println("------------ POST에 달린 comment 내용 조회 완료 ------------\n\n");
    }

    private void saveSampleData() {
        final String postTitleFormat = "[%d] post-title";
        final String postContentFormat = "[%d] post-content";
        final String commentContentFormat = "[%d] comment-content";

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Post post = new Post(format(postTitleFormat, i), format(postContentFormat, i));

            IntStream.rangeClosed(1, 3).forEach(j -> {
                post.writeComment(format(commentContentFormat, j));
            });

            postRepository.save(post);
        });
    }
}
