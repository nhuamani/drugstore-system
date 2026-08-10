package com.nhuamani.drugstore_system.configs;

import com.nhuamani.drugstore_system.security.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .authorizeHttpRequests(authorize -> authorize

                        // =========================
                        // RECURSOS PÚBLICOS
                        // =========================

                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico",
                                "/webjars/**"
                        ).permitAll()

                        // =========================
                        // ROLES
                        // =========================

                        // Solo ADMIN
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // ADMIN + MANAGER
                        .requestMatchers("/manager/**")
                        .hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // ADMIN + MANAGER + EMPLOYEE
                        .requestMatchers("/employee/**")
                        .hasAnyRole(
                                "ADMIN",
                                "MANAGER",
                                "EMPLOYEE"
                        )


                        // =========================
                        // PERMISOS
                        // =========================

                        .requestMatchers("/users/create")
                        .hasAuthority("USER_CREATE")

                        .requestMatchers("/users/edit/**")
                        .hasAuthority("USER_UPDATE")

                        .requestMatchers("/users/delete/**")
                        .hasAuthority("USER_DELETE")


                        // =========================
                        // RESTO
                        // =========================

                        .anyRequest()
                        .authenticated()
                )


                // =========================
                // LOGIN
                // =========================

                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl(
                                "/dashboard",
                                true
                        )
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // =========================
                // LOGOUT
                // =========================

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
