package com.github.vitorhenriquec.carrental.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitorhenriquec.carrental.repository.UserRepository;
import com.github.vitorhenriquec.carrental.security.AuthenticationFilter;
import com.github.vitorhenriquec.carrental.security.AuthorizationFilter;
import com.github.vitorhenriquec.carrental.security.JwtUtil;
import com.github.vitorhenriquec.carrental.service.UserDetailsCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    private final JwtUtil jwtUtil;

    private final UserDetailsCustomService userDetailsCustomService;


    private final String[] whiteList = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };


    private final String[] postWhiteList = {
            "/v1/user"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests(
                auth -> {
                    auth.antMatchers(HttpMethod.POST, postWhiteList).permitAll();
                }
        );
        http.authorizeRequests(
                auth -> {
                    auth.anyRequest().authenticated();
                }
        );
        http.addFilter(new AuthenticationFilter(authenticationManager(), userRepository, objectMapper, jwtUtil));
        http.addFilter(new AuthorizationFilter(authenticationManager(), userDetailsCustomService, jwtUtil));
        http.sessionManagement(
                sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }
        );
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsConfig(){
        final var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(whiteList);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsCustomService).passwordEncoder(bcryptPasswordEncoder());
    }
}
