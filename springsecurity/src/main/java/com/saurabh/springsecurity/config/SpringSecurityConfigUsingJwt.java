package com.saurabh.springsecurity.config;

import com.saurabh.springsecurity.jwt.JwtConfig;
import com.saurabh.springsecurity.jwt.JwtTokenVerifier;
import com.saurabh.springsecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.saurabh.springsecurity.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMongoRepositories(basePackages = {"com.saurabh.springsecurity.repository"})
public class SpringSecurityConfigUsingJwt extends WebSecurityConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("applicationUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    @Qualifier("accessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    @Qualifier("restAuthenticationEndpointHandler")
    private AuthenticationEntryPoint authenticationEntryPoint;

    //Using Jwt Authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF is required when request goes from browser, otherwise not required.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt is stateless, Added here
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig)) //Filter
                .addFilterAfter(new JwtTokenVerifier(jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*", "/signup/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v2/**").hasRole(Role.ADMIN.getMessage()) //POST Api starts with /api/v2/** can be access by ADMIN Role.
                .antMatchers(HttpMethod.DELETE, "/api/v2/**").hasAuthority(Role.ADMIN.getMessage()) //DELETE Api starts with /api/v2/** can be access by ADMIN Role.
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority(Role.STUDENT.getMessage()) //GET Api starts with /api/ can be access by STUDENT.
                .anyRequest()
                .authenticated()
                /*.and()
                .exceptionHandling() not working as expected hence commented
                .accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)*/;
    }

    //Spring Security generates user while startup with password as uid and store into inmemory db. which lost when application stops. Will create our own
    //Spring security accepts password with Password encoder, without that it won't work


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
