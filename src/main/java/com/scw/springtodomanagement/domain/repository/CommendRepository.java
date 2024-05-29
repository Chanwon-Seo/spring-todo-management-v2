package com.scw.springtodomanagement.domain.repository;

import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.common.exception.errorcode.CommendErrorCode;
import com.scw.springtodomanagement.common.exception.errorcode.commend.CommendUnauthorizedException;
import com.scw.springtodomanagement.domain.entity.Commend;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface CommendRepository extends JpaRepository<Commend, Long> {

    Optional<Commend> findById(Long postId);

    List<Commend> findByPost(Post post);

    default Commend findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ApiException(CommendErrorCode.NOT_FOUND_COMMEND));
    }

}
