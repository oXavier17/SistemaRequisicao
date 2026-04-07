package com.example.Sistema_Requisicao.controllers;
/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Desativa para APIs Stateless
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Libera o Login
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() // Libera o Registro
                .anyRequest().authenticated() // Tranca todo o resto
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Para salvar senhas criptografadas (Essencial!)
    }
}*/