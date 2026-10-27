
package be.ehb.thebookshop.config;

import be.ehb.thebookshop.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            UserRepository userRepository) {

        return email -> userRepository.findByEmail(email)
                .map(user -> User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Gebruiker niet gevonden"));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    org.springframework.web.cors.CorsConfiguration config =
                            new org.springframework.web.cors.CorsConfiguration();

                    config.setAllowedOrigins(
                            java.util.List.of("http://127.0.0.1:5500")
                    );
                    config.setAllowedMethods(
                            java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    );
                    config.setAllowedHeaders(
                            java.util.List.of("*")
                    );
                    config.setAllowCredentials(true);

                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/books",
                                "/api/books/**"
                        ).permitAll()
                        .requestMatchers("/api/users/me",
                                "/api/reservations", "/api/reservations/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

}
