package com.epam.esm.spring.web.config;

import com.epam.esm.spring.web.exception.ControllerAdvisor;
import com.epam.esm.spring.web.exception.CustomAccessDeniedHandler;
import com.epam.esm.spring.web.exception.CustomAuthenticationException;
import com.epam.esm.spring.web.exception.JsonResponseSender;
import com.epam.esm.spring.web.security.JwtConfigurer;
import com.epam.esm.spring.web.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTHENTICATION_ERROR_MSG = "error.incorrect_token";
    private static final String ROLE_ROOT = "ROOT";
    private static final String ACTUATOR_ENDPOINT = "/actuator";

    private final JwtTokenFilter jwtTokenFilter;
    private final JwtConfigurer jwtConfigurer;
    private final ControllerAdvisor controllerAdvisor;
    private final JsonResponseSender jsonResponseSender;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ACTUATOR_ENDPOINT).hasRole(ROLE_ROOT)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(
                        new CustomAccessDeniedHandler(jsonResponseSender, controllerAdvisor))
                .and()
                .exceptionHandling().authenticationEntryPoint((httpServletRequest, httpServletResponse, e) ->
                        handleError(httpServletRequest, httpServletResponse))
                .and()
                .apply(jwtConfigurer);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object responseObject = controllerAdvisor.handleAuthenticationException(
                new CustomAuthenticationException(AUTHENTICATION_ERROR_MSG),
                request.getLocale()
        );

        jsonResponseSender.send(response, responseObject);
    }
}
