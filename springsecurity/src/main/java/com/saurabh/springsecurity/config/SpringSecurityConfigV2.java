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

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfigV2 extends WebSecurityConfigurerAdapter {

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
                        .antMatchers(HttpMethod.POST, "/api/v2/**").hasRole(String.valueOf(ApplicationRole.ADMIN)) //POST Api starts with /api/v2/** can be access by ADMIN Role.
                        .antMatchers(HttpMethod.DELETE, "/api/v2/**").hasRole(String.valueOf(ApplicationRole.ADMIN)) //DELETE Api starts with /api/v2/** can be access by ADMIN Role.
                        .antMatchers(HttpMethod.GET, "/api/**").hasRole(ApplicationRole.STUDENT.getMsg()) //GET Api starts with /api/ can be access by STUDENT.
                        .anyRequest()
                        .authenticated()
                .and()
                    .formLogin() // till here, it will open default login page
                        .loginPage("/CustomLogin").permitAll() // Custom Login page Will open
                        .loginProcessingUrl("/login")
                        .usernameParameter("username") //name of html element, default is username
                        .passwordParameter("password") ////name of html element, default is password
                        .defaultSuccessUrl("/home",true)
                .and()
                    .rememberMe() // Default time of session expiry
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)) // can increase token validity
                    .key("TestKey")
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/CustomLogin")
                    */
/*.rememberMeParameter("remember-me")*//*
; //Default name is 'remember-me'

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
                .build();

        UserDetails sakshi = User
                .builder()
                .username("sakshi")
                .password(passwordEncoder.encode("1234"))
                .roles(ApplicationRole.STUDENT.getMsg()) //ROLE_STUDENT
                .build();
        return new InMemoryUserDetailsManager(saurabh, sakshi);
    }
}
*/
//This is working Code, using form login Auth with In memoery Database-->