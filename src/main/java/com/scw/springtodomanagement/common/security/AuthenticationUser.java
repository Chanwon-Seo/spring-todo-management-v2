package com.scw.springtodomanagement.common.security;

import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.enums.MemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationUser implements UserDetails {

    /**
     * User Entity username
     */
    private final String username;
    /**
     * User Entity password
     */
    private final String password;
    /**
     * User Entity userRole
     */
    @Getter
    private MemberRoleType memberRoleType;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + memberRoleType.getRoleName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public static AuthenticationUser of(Member member) {
        return AuthenticationUser.builder()
                .username(member.getUserName())
                .password(member.getPassword())
                .memberRoleType(member.getMemberRoleType())
                .build();
    }
}
