package com.company.springforum.security.filter;

import com.company.springforum.model.Roles;
import com.company.springforum.model.User;
import com.company.springforum.repository.UserRepository;
import com.company.springforum.repository.UserSecurityRepo;
import com.company.springforum.security.jwt.JwtUserDetails;
import com.company.springforum.security.jwt.RolesSecurity;
import com.company.springforum.security.jwt.UserSecurity;
import com.company.springforum.security.jwtutil.JwtUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class JwtTokenVerifyFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final UserSecurityRepo userRepository;


    public JwtTokenVerifyFilter(UserDetailsService userDetailsService, UserSecurityRepo userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorizationHeader = request.getHeader("Authorization");
        String oauth2 = request.getHeader("Oauth2");
        System.out.println(oauth2);
        System.out.println(" jwt filter works");
        if ((Strings.isNullOrEmpty(oauth2) || !oauth2.startsWith("oauth2 ")) &&
                (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer "))) {
            chain.doFilter(request, response);
            return;
        }


        if (authorizationHeader.startsWith("Bearer ")){
            jwtParser(authorizationHeader,request,response,chain);
        } else {
            oauthParser(oauth2,request,response,chain);
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }

    public void jwtParser(String authorizationHeader, HttpServletRequest request, HttpServletResponse response ,FilterChain chain) throws IOException, ServletException {
        String token = authorizationHeader.replace("Bearer ","");
        String email = JwtUtil.extractCredentials(token);
        JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(email);
        List<SimpleGrantedAuthority> grantedAuthorityList = JwtUtil.extractAuthorities(token);
        if (jwtUserDetails.getUsername().equals(email) && jwtUserDetails.getAuthorities().equals(grantedAuthorityList)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    jwtUserDetails.getUsername(),
                    jwtUserDetails.getPassword(),
                    jwtUserDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(request.getHeader("check") + "this here");
            response.setHeader("check",jwtUserDetails.getUsername());
        }
        chain.doFilter(request,response);
    }
    public void oauthParser(String oauth2, HttpServletRequest request, HttpServletResponse response ,FilterChain chain) throws IOException, ServletException {
           String userNum = oauth2.replace("oauth2 ","");
           System.out.println(userNum + " user");
           UserSecurity user = userRepository.findByUserNum(userNum);
           if (Objects.isNull(user)) {
               chain.doFilter(request, response);
               return;
           } else {
             List<RolesSecurity> roles = user.getRoles();
             List<SimpleGrantedAuthority> rolesList = new ArrayList<>();
               for(RolesSecurity role : roles){
                   rolesList.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
               }
               Authentication authentication = new UsernamePasswordAuthenticationToken(
                       user.getEmail(),
                       user.getPassword(),
                       rolesList
               );
               SecurityContextHolder.getContext().setAuthentication(authentication);
               chain.doFilter(request,response);
           }
     }
}

