package be.ehb.thebookshop.config;

import be.ehb.thebookshop.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> userRepository.findByEmail(email)
                .map(user -> User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Gebruiker niet gevonden"));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository) throws Exception {

        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    // NIEUW: beide varianten van je frontend toelaten
                    config.setAllowedOrigins(List.of(
                            "http://127.0.0.1:5500",
                            "http://localhost:5500"));
                    config.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()))
                .securityContext(context -> context
                        .securityContextRepository(securityContextRepository))
                // NIEUW: niet ingelogd geeft 401 in plaats van 403
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/books",
                                "/api/books/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/books/*/reviews").authenticated()
                        .requestMatchers(
                                "/api/users/me",
                                "/api/reservations",
                                "/api/reservations/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}