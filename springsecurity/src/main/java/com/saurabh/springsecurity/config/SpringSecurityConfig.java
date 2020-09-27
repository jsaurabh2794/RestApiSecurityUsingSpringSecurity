/*
package com.saurabh.springsecurity;

import com.saurabh.springsecurity.model.ApplicationRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    */
/*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(); // basic Authentication
    }*//*


    */
/*@Override
    protected void configure(HttpSecurity http) throws Exception {
        //Using ant-matcher, we can whitelist some url
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(); // basic Authentication using antmatcher which whitelist url based on regex
    }*//*


    //using Role and Authority
    */
/*@Override
    protected void configure(HttpSecurity http) throws Exception {
        //Using ant-matcher, we can whitelist some url
        http
                .csrf().disable() // CSRF is required when request goes from browser, otherwise not required.
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/v1/**").hasRole(String.valueOf(ApplicationRole.STUDENT))
                .antMatchers(HttpMethod.DELETE,"/api/v2/**").hasAnyAuthority("WRITE")
                .antMatchers(HttpMethod.POST,"/api/v2/**").hasAnyAuthority("WRITE")
                .antMatchers(HttpMethod.GET,"/api/**").hasAnyAuthority("READ","WRITE")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(); // basic Authentication using antmatcher which whitelist url based on regex
    }*//*


    //Using Form Authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Using ant-matcher, we can whitelist some url
        http
                .csrf().disable() // CSRF is required when request goes from browser, otherwise not required.
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/v1/**").hasAnyRole(String.valueOf(ApplicationRole.STUDENT), String.valueOf(ApplicationRole.ADMIN))
                .antMatchers(HttpMethod.DELETE,"/api/v2/**").hasAnyAuthority("WRITE")
                .antMatchers(HttpMethod.POST,"/api/v2/**").hasAnyAuthority("WRITE")
                .antMatchers(HttpMethod.GET,"/api/**").hasAnyAuthority("READ","WRITE")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin() // till here, it will open default login page
                */
/*.loginPage("/CustomLogin").permitAll()*//*
; // Custom Login page Will open

        //Default login handler is /login with POST username and password
    }

    //Spring Security generates user while startup with password as uid and store into inmemory db. which lost when application stops. Will create our own
    //Spring security accepts password with Password encoder, without that it won't work
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails saurabh = User
                .builder()
                .username("saurabh")
                .password(passwordEncoder.encode("1234"))
                .roles(ApplicationRole.ADMIN.getMsg()) //ROLE_ADMIN
                .authorities("WRITE","READ")
                .build();

        UserDetails sakshi = User
                .builder()
                .username("sakshi")
                .password(passwordEncoder.encode("1234"))
                .roles(ApplicationRole.STUDENT.getMsg()) //ROLE_ADMIN
                .authorities("READ")
                .build();
        return new InMemoryUserDetailsManager(saurabh,sakshi);
    }
}
*/

//Its Working class, Basic authentication using in memory Db