package com.saurabh.springsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //This class will authenticate the credentials which will be sent by the client
    private static final Logger logger = LogManager.getLogger(JwtUsernameAndPasswordAuthenticationFilter.class);


    private AuthenticationManager authenticationManager;
    private JwtConfig jwtConfig;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authenticationResult;
        try {
            //Grab the username and password from the request
            UserNameAndPasswordForAuthenticationModel nameAndPasswordForAuthentication = new ObjectMapper()
                    .readValue(request.getInputStream(), UserNameAndPasswordForAuthenticationModel.class);
            //Authenticate the username and password
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    nameAndPasswordForAuthentication.getUsername(),
                    nameAndPasswordForAuthentication.getPassword());

            //Authenticate the username and password  and will check username and password is correct or not
            authenticationResult = authenticationManager.authenticate(authentication);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationResult;
    }

    //After successful authentication of credentials, I mean after successful execution of attemptAuthentication() method, this method will be invoked
    //Once authentication is success, will generate the jwt token.

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        String token = null;
        try {
            token = Jwts.builder()
                    .setSubject(authResult.getName())
                    .claim(jwtConfig.getClaimHeaderName(), authResult.getAuthorities())
                    .setIssuedAt(new java.util.Date())
                    .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getNoOfDaysToExpireToken())))
                    .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecureKey().getBytes()))
                    .compact();
        } catch (Exception ex) {
            logger.error("Exception occurred:" + ex);
            throw ex;
        }

        //set the token into response, it will reach to client
        response.addHeader(jwtConfig.getTokenHeaderName(), jwtConfig.getTokenPrefix() + " " + token);
        StringBuilder sb = new StringBuilder("")
                .append("{")
                .append("\"Token\":")
                .append("\"")
                .append(jwtConfig.getTokenPrefix() + " " + token)
                .append("\"}");
        response.getWriter().println(sb);
    }
}
