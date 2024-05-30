package com.scw.springtodomanagement.domain.repository;

import com.scw.springtodomanagement.domain.entity.MemberLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLoginHistoryRepository extends JpaRepository<MemberLoginHistory, Long> {
}
