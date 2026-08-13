package com.nhuamani.drugstore_system.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize

                        // =========================
                        // RECURSOS PÚBLICOS
                        // =========================

                        .requestMatchers(
                                "/login",
                                "/403",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico",
                                "/webjars/**"
                        ).permitAll()

                        // =========================
                        // ROLES
                        // =========================

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // ADMIN + MANAGER
                        .requestMatchers("/manager/**")
                        .hasAnyRole("ADMIN", "MANAGER")

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
                // ACCESS DENIED
                // =========================

                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403")
                )

                // =========================
                // LOGIN
                // =========================

                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // =========================
                // LOGOUT
                // =========================

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
