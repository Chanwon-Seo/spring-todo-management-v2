package com.scw.springtodomanagement.common.security;

import com.scw.springtodomanagement.common.exception.user.LoginUserNotFoundException;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserName(username).orElseThrow(
                LoginUserNotFoundException::new);

        return AuthenticationUser.of(member);
    }
}
