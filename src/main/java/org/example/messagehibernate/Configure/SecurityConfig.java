package org.example.messagehibernate.Configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // השבתת CSRF
                .authorizeHttpRequests(auth -> auth
                          .anyRequest().permitAll() //  .requestMatchers("/people/**") הרשאה לנתיב ספציפי
                       // כל שאר הבקשות דורשות אימות
                );

        return http.build();
    }
}
