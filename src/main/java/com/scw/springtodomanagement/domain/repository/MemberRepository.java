package com.scw.springtodomanagement.domain.repository;

import com.scw.springtodomanagement.common.exception.user.LoginUserNotFoundException;
import com.scw.springtodomanagement.common.exception.user.MemberDuplicateException;
import com.scw.springtodomanagement.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserName(String username);

    default Member findByUsernameOrElseThrow(String username) {
        return findByUserName(username).orElseThrow(LoginUserNotFoundException::new);
    }

    default void checkDuplicateUsername(String username) {
        findByUserName(username)
                .ifPresent(m -> {
                    throw new MemberDuplicateException();
                });
    }
}
