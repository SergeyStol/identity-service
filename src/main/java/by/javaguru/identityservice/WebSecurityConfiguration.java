package by.javaguru.identityservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
          .requestMatchers(HttpMethod.POST, "/v1.0/auth/register").permitAll()
          .requestMatchers(HttpMethod.POST, "/v1.0/auth/token").permitAll()
          .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
          .anyRequest().authenticated())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();

   }
}