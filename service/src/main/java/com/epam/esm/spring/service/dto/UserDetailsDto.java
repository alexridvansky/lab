package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto extends AbstractDto implements UserDetails {

    private String username;
    private String password;
    private List<? extends GrantedAuthority> authorities;
    private boolean isActive;

    @Builder
    public UserDetailsDto(long id, String username, List<? extends GrantedAuthority> authorities, String password,
                          boolean isActive) {
        super(id);
        this.username = username;
        this.authorities = authorities;
        this.password = password;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
