package ua.com.foxminded.universityapp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.com.foxminded.universityapp.service.impl.CustomUserDetailsService;

import java.util.Random;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class Config implements WebMvcConfigurer {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public Config(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/schedule/all", true)
                )
                .logout(LogoutConfigurer::permitAll)
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Override
    public void addViewControllers(@NotNull ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        WebMvcConfigurer.super.addViewControllers(registry);
    }
}