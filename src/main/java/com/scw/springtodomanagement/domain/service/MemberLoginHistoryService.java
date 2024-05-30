package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.domain.entity.MemberLoginHistory;
import com.scw.springtodomanagement.domain.repository.MemberLoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberLoginHistoryService {
    private final MemberLoginHistoryRepository memberLoginHistoryRepository;
    @Transactional
    public void memberLoginHistorySave(MemberLoginHistory memberLoginHistory) {
        memberLoginHistoryRepository.save(memberLoginHistory);
    }
}
