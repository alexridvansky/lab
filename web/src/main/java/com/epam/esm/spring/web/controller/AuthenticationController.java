package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.JpaUserService;
import com.epam.esm.spring.service.dto.AuthenticationRequestDto;
import com.epam.esm.spring.service.dto.AuthenticationResponseDto;
import com.epam.esm.spring.service.dto.UserResponseDto;
import com.epam.esm.spring.web.exception.CustomAuthenticationException;
import com.epam.esm.spring.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final String AUTHENTICATION_ERROR_MESSAGE = "error.wrong_username_password_combination";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JpaUserService jpaUserService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authRequestDto) {
        try {
            String username = authRequestDto.getUsername();
            String password = authRequestDto.getPassword();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authentication);

            UserResponseDto user = jpaUserService.findByUsername(username);

            // pretty much impossible scenario after authenticate() method...
            if (user == null) {
                throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE);
            }

            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());
            AuthenticationResponseDto authResponse = new AuthenticationResponseDto(user.getUsername(), token);

            return ResponseEntity.ok(authResponse);

        } catch (AuthenticationException e) {
            throw new CustomAuthenticationException(AUTHENTICATION_ERROR_MESSAGE);
        }
    }
}
