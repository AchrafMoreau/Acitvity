package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception{
        return https
                .csrf(crsf -> crsf.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/users/signin").permitAll()
                            .requestMatchers("/users/signup").permitAll()
                            .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exeption ->
                        exeption.accessDeniedHandler((req, res, accessDeniedhanddel) ->
                                        res.sendRedirect("/login")
                        )
                )
                .addFilterBefore(new JwtTokenFIlter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();


    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
