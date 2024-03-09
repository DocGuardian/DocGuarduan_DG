package mhkif.yc.docguardian.config.security;

import lombok.AllArgsConstructor;
import mhkif.yc.docguardian.config.security.jwt.JwtAuthenticationFilter;
import static mhkif.yc.docguardian.enums.Role.ADMIN;
import static mhkif.yc.docguardian.enums.Role.USER;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers(
                                        "doc_guardian/api/v1/auth/**"
                                        ).permitAll()
                                .requestMatchers(
                                        "doc_guardian/api/v1/rooms/**"
                                ).hasRole(ADMIN.name())
                                .requestMatchers(
                                        "doc_guardian/api/v1/users/**"
                                ).hasAnyRole(ADMIN.name(), USER.name())
                                .anyRequest().authenticated())

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "PUT", "PATCH", "DELETE"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }













    /*
   // @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(adminAuthenticationProvider(), companyAuthenticationProvider());
    }

    //@Bean
    public AdminAuthenticationProvider adminAuthenticationProvider() {
        return new AdminAuthenticationProvider(passwordEncoder());
    }

    //@Bean
    public CompanyAuthenticationProvider companyAuthenticationProvider() {
        return new CompanyAuthenticationProvider(passwordEncoder());
    }


     */
}
