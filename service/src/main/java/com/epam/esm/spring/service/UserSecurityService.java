package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.jparepository.UserRepository;
import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.dto.UserDetailsDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new EntryNotFoundException(ERROR_USER_NOT_FOUND, username));

        return UserDetailsDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .isActive(user.isActive())
                .build();
    }
}