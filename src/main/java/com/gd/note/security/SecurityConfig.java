package com.gd.note.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests((requests) ->
        //         requests
        //                 .requestMatchers("/contact").permitAll()
        //                 .requestMatchers("/public/**").permitAll()
        //                 .requestMatchers("/admin").denyAll()
        //                 .requestMatchers("/admin/**").denyAll()
        //                 .anyRequest().authenticated());
        // http.formLogin(withDefaults());

        // http.csrf(csrf -> csrf.disable());

        // http.sessionManagement(session ->
        //         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.authorizeHttpRequests((requests) ->
                requests.anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);

        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager =
                new JdbcUserDetailsManager(dataSource);
        if (!manager.userExists("uu")) {
            manager.createUser(
                    User.withUsername("uu")
                            .password("{noop}uu")
                            .roles("USER")
                            .build()
            );
        }
        if (!manager.userExists("aa")) {
            manager.createUser(
                    User.withUsername("aa")
                            .password("{noop}aa")
                            .roles("ADMIN")
                            .build()
            );
        }
        return manager;
    }
}
