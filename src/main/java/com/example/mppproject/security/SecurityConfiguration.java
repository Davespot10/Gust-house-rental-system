package com.example.mppproject.security;

import com.google.cloud.storage.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private UserDetailsService userDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();
        http.authorizeRequests()
                // Our public endpoints
                .antMatchers(String.valueOf(HttpMethod.GET), "/api/v1/login").permitAll()
                .antMatchers(String.valueOf(HttpMethod.POST), "/api/v1/property").permitAll()
                .antMatchers(String.valueOf(HttpMethod.GET), "/api/v1/property{id}").permitAll()
                .antMatchers(String.valueOf(HttpMethod.GET), "/api/v1/AppUser").permitAll()
                .antMatchers(String.valueOf(HttpMethod.POST), "/api/v1/AppUser").permitAll();



                // Our private endpoints
//                .antMatchers("/api/admin/user/**").hasRole(Role.USER_ADMIN)
//                .antMatchers("/api/author/**").hasRole(Role.AUTHOR_ADMIN)
//                .antMatchers("/api/book/**").hasRole(Role.BOOK_ADMIN)
//                .anyRequest().authenticated();

        http.addFilterBefore(
                jwtRequestFilter,
                UsernamePasswordAuthenticationFilter.class
        );
//        http.cors().and().csrf().disable().
//                authorizeRequests()
//                .antMatchers("/api/v1/login").permitAll()
//                .antMatchers("/api/v1/property").permitAll()
//
//                .antMatchers("/api/v1/property{id}").permitAll()
//                .anyRequest().authenticated()
//                .and()
////                .formLogin().and()
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, authException) ->
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
//                ).and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
