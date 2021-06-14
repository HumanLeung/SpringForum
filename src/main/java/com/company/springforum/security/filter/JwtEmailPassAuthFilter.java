package com.company.springforum.security.filter;

import com.company.springforum.security.jwt.JwtEmailPassAuthRequest;
import com.company.springforum.security.jwtutil.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtEmailPassAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtEmailPassAuthFilter(AuthenticationManager authenticationManager) {
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/entry/login","POST"));
        this.authenticationManager = authenticationManager;
        setUsernameParameter("email");
        System.out.println("pass");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        JwtEmailPassAuthRequest authRequest = null;
        System.out.println("password");
        try {
            authRequest = new ObjectMapper().readValue(request.getInputStream(), JwtEmailPassAuthRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert authRequest != null;
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        );
        return authenticationManager.authenticate(authentication);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println(authResult + "this is authResult");
        String token = JwtUtil.generateToken(authResult);
        System.out.println(authResult.getName()+" "+authResult.getAuthorities()+" "+authResult.getDetails());
       response.addHeader("Authorization","Bearer "+ token);
    }
}
