package com.scw.springtodomanagement;

import com.scw.springtodomanagement.domain.entity.Image;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.enums.MemberRoleType;
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
            save(
                    Member.builder()
                            .nickName("서찬투투투")
                            .userName("test@gmail.com")
                            .password(passwordEncoder.encode("test1234"))
                            .memberRoleType(MemberRoleType.USER)
                            .build()
            );
        }

        public void save(Object... objects) {
            for (Object object : objects) {
                em.persist(object);
            }
        }
    }
}

