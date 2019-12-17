package com.softeng.votit.security.jwt;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng.votit.model.dto.user.LoginDto;
import com.softeng.votit.model.dto.user.SecurityDto;
import com.softeng.votit.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        LoginDto loginDto = null;

        try {
            loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword());

        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX +  token);

        SecurityDto securityDto = new SecurityDto();
        securityDto.setToken(response.getHeader("Authorization"));
        securityDto.setEmail(principal.getUsername());
        for(GrantedAuthority grantedAuthority: principal.getAuthorities()){
            securityDto.getRoles().add(grantedAuthority.getAuthority());
        }


        byte[] responseByte = restResponseBytes(securityDto);
        response.getOutputStream().write(responseByte);


    }

    private byte[] restResponseBytes(SecurityDto securityDto) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(securityDto);
        return serialized.getBytes();
    }

}
