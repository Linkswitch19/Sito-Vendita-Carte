package ch.supsi.cardgames.config;

import ch.supsi.cardgames.models.UserRole;
import ch.supsi.cardgames.security.CustumUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public SecurityConfig(CustumUserDetailService custumUserDetailService) {
        this.custumUserDetailService = custumUserDetailService;
    }

    CustumUserDetailService custumUserDetailService;
    @Bean
    @Order(1) // priorità esecuzione

    public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        return httpSecurity.csrf(AbstractHttpConfigurer :: disable)
                .formLogin(form-> form.loginPage("/login").failureUrl("/login?error").permitAll())
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/","/register","login","/css/**", "/scripts/**", "/immagini/**", "/webjars/**","/card/search").permitAll()

                        .requestMatchers("/card/new","/card/").authenticated()
                        .requestMatchers("/card/edit/","card/delete/").hasRole(UserRole.ADMIN.name()) ///  na solo mette ROLE_
                        .anyRequest().authenticated()
                )
                .logout(longout-> longout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll() )
                .userDetailsService(custumUserDetailService) //colegamento con le imp personalizzate non obbligatorio
                .build();

    }

    @Bean // classe
    PasswordEncoder BCPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
