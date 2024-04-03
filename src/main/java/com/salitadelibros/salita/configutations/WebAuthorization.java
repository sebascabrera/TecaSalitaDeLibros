package com.salitadelibros.salita.configutations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        http.authorizeRequests()
                        .antMatchers("/h2-console","/registro", "/ingreso", "/js/**", "/styles/**").permitAll()
                        .antMatchers(HttpMethod.POST,"/login","/logout","index.html").permitAll()
                        .antMatchers(HttpMethod.POST,"/asociarDatos").hasAuthority("ADMIN")
                        .antMatchers( "/formulario").hasAuthority("ADMIN");
        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/index");

        http.logout().logoutUrl("/logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }
}
