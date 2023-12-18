package com.lec.spring.repository;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private UserService userService;

    @Test
    void test(){
        UserRepository userRepository = sqlSession.getMapper(UserRepository.class);
        AuthorityRepository authorityRepository = sqlSession.getMapper(AuthorityRepository.class);

        // 유저 1번 정보찾고 밑에 리스트로 권한뽑기
        User user = userRepository.findById(1L);
        System.out.println("findById(): " + user);

        List<Authority> list = authorityRepository.findByUser(user);
        System.out.println("권한들: " + list);

        // ADMIN유저 정보찾고 밑에 리스트로 권한뽑기
        user = userRepository.findByUsername("ADMIN1");
        System.out.println("findByUsername(): " + user);

        list = authorityRepository.findByUser(user);
        System.out.println("권한들: " + list);
    }

    @Test
    void test2(){
        User user = User.builder()
                .username("toTo")
                .password("1234")
                .email("toto@mail.com")
                .name("토토")
                .build();

        int cnt = userService.register(user);
        System.out.println("register 결과: " + cnt + " : " + user);

        user = userService.findByUsername("TOTO");
        System.out.println("findByUsername: " + user);

        List<Authority> list = userService.selectAuthoritiesById(user.getId());
        System.out.println("selectAuthoritiesById: " + list);
    }

}