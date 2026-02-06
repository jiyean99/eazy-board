package com.beyond.basic.common.configs;

import com.beyond.basic.common.auth.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public SecurityConfig(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    // 세션 로그인의 흐름
    // 1. 화면에서 doLogin API 호출
    // 2. Spring 에서 사전에 구현된 doLogin 메서드에서 loadUserByUsername 메서드 호출
    // 3. loadUserByUsername 메서드에서 조회된 User 객체와 사용자가 입력한 email, password와 비교 검증
    // 4. 검증완료시, DB의 user 객체를 사용하여 Authentication 객체로 저장 및 세션 ID 발급
    // 5. 사용자는 세션 ID를 발급받고, 서버는 세션ID를 저장하여, API 요청시마다 세션ID를 검증
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeRequests().antMatchers(
                        "/",
                        "/author/create",
                        "/author/login",
                        "/posts")
                .permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                // 인증이 되지 않는 경우 라우팅
                .loginPage("/author/login")
                .loginProcessingUrl("/doLogin")
                // 사전에 구현돼 있는 doLogin 메서드 그대로 사용
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler)
                .and()
                .logout().logoutUrl("/doLogout")
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}