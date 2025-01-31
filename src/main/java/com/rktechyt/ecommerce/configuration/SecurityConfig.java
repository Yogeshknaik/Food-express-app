package com.rktechyt.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rktechyt.ecommerce.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    @Autowired
    CustomUserDetailService customUserDetailService;

    public final String[] WHITELIST = {
        "/", "/home", "/shop/**", "/db-console/**", "/register",
        "/resources/**", "/static/**", "/css/**", "/images/**", 
        "/productImages/**", "/js/**"
    };
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeHttpRequests(request -> {
            // httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(WHITELIST).permitAll());
            // httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated());
            request.requestMatchers(WHITELIST).permitAll();
            request.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated();
        }).formLogin(formLogin -> {
            // httpSecurity.formLogin(formLogin -> formLogin.loginPage("/login").permitAll().failureUrl("/login?error=true").defaultSuccessUrl("/").usernameParameter("email").passwordParameter("password"));
            formLogin.loginPage("/login").permitAll().defaultSuccessUrl("/").failureUrl("/login?error=true").usernameParameter("email").passwordParameter("password");
        }).oauth2Login(o2Auth -> {
            // httpSecurity.oauth2Login(o2Auth -> o2Auth.loginPage("/login").successHandler(googleOAuth2SuccessHandler).defaultSuccessUrl("/"));
            o2Auth.loginPage("/login").successHandler(googleOAuth2SuccessHandler).defaultSuccessUrl("/home", true);
        }).logout(logout -> {
            // httpSecurity.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID"));
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").invalidateHttpSession(true);
        }).csrf(csrf -> {
            // httpSecurity.csrf(csrf -> csrf.disable());
            csrf.disable();
        }).headers(headers -> {
            headers.frameOptions(frameOption -> {
                // httpSecurity.headers(header -> header.frameOptions(frameOption -> frameOption.disable()));
                frameOption.disable();
            });
            // httpSecurity.userDetailsService(customUserDetailService);
            // return httpSecurity.build();
        }).userDetailsService(customUserDetailService).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
