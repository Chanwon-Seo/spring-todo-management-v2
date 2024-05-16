package com.scw.springtodomanagement;

import com.scw.springtodomanagement.domain.entity.Image;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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

        public void dbInit1() {

            save(
                    Image.builder()
                            .originalFilename("test.png")
                            .extractFilename("0e040ae5-24bf-45e5-8b9d-ddbcad6e6d65.png")
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

