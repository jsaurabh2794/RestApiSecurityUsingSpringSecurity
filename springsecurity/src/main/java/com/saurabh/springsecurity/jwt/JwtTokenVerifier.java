package com.saurabh.springsecurity.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    final String authority = "authority";

    private JwtConfig jwtConfig;

    public JwtTokenVerifier(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // get Authorization Header from client
        String autorizationheader = request.getHeader(jwtConfig.getTokenHeaderName());

        //Check header is null or not starts with Bearer
        if (Strings.isNullOrEmpty(autorizationheader) || !autorizationheader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);  //pass request and response to next filter in the chain
            return; //Will reject the request
        }

        String token = autorizationheader.replace(jwtConfig.getTokenPrefix(), "");
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSecureKey().getBytes()))
                    .build()
                    .parseClaimsJws(token);// From Documentation: compacting it into its final String form. A signed JWT is called a 'JWS'.

            Claims body = claimsJws.getBody();
            String userName = body.getSubject(); // Subject is username, while creation of token set subject as username
            List<HashMap<String, String>> authorities = (List<HashMap<String, String>>) body.get(jwtConfig.getClaimHeaderName());//Key that We have set while token generation

            //Create list of Type which extends GrantedAuthority
            Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(m -> {
                        return new SimpleGrantedAuthority(m.get(authority));
                    }) //key is "authority", because jwt payload contains data with key as "authority"
                    .collect(Collectors.toSet());


            //if We reach here, means it authenticated successfully
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userName,
                    null,
                    grantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            logger.error("Exception occurred:" + e);
            response.getWriter().println(String.format("Token %s can not be trusted", token));
            throw new IllegalStateException(String.format("Token %s can not be trusted", token), e);
        } catch (Exception ex) {
            logger.error("Exception occurred:" + ex);
        }
        //once everything is done, pass request and response to next filter in the chain
        filterChain.doFilter(request, response);
    }
}
