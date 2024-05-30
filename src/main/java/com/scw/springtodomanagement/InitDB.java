package com.scw.springtodomanagement;

import com.scw.springtodomanagement.domain.entity.Commend;
import com.scw.springtodomanagement.domain.entity.Image;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import com.scw.springtodomanagement.domain.entity.enums.MemberRoleType;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        @Autowired
        PasswordEncoder passwordEncoder;

        public void dbInit1() {

            save(
                    Image.builder()
                            .originalFilename("test.png")
                            .extractFilename("0e040ae5-24bf-45e5-8b9d-ddbcad6e6d65")
                            .build()
            );
            Member member = Member.builder()
                    .nickName("새로운닉네임")
                    .userName("newUser@gmail.com")
                    .password(passwordEncoder.encode("sparta1234!"))
                    .memberRoleType(MemberRoleType.USER)
                    .build();
            save(member);

            Post post = Post.builder()
                    .title("1")
                    .content("asdfasdf")
                    .member(member)
                    .postStateType(PostStateType.ENABLE)
                    .build();
            save(post);
//
//            Commend.CommendBuilder commend = Commend.builder()
//                    .contents("수정전 내용")
//                    .member(member)
//                    .post(post);
//
//            save(commend);
        }

        public void save(Object... objects) {
            for (Object object : objects) {
                em.persist(object);
            }
        }
    }
}

