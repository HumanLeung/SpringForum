package com.company.springforum.config;


import com.company.springforum.repository.UserSecurityRepo;
import com.company.springforum.security.filter.JwtEmailPassAuthFilter;
import com.company.springforum.security.filter.JwtTokenVerifyFilter;
import com.company.springforum.security.oauth2.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final Oauth2UserService oauth2UserService;
    private final UserSecurityRepo repo;


    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          @Qualifier("JwtService") UserDetailsService userDetailsService
                          , Oauth2UserService oauth2UserService, UserSecurityRepo repo) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.oauth2UserService = oauth2UserService;
        this.repo = repo;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authFilter())
                .addFilterAfter(new JwtTokenVerifyFilter(userDetailsService,repo),
                        JwtEmailPassAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/","/static/**","/myforum/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/login/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/entry/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/entry/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole("user")
                .antMatchers(HttpMethod.OPTIONS,"/api/article/**").hasAnyRole("user")
                .antMatchers(HttpMethod.GET,"/api/article/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/article/**").permitAll()
                .antMatchers("/api/oauth2/**").permitAll()
                .antMatchers("/webchat/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/article/**").permitAll()
                .antMatchers("/hello").hasAnyRole("user")
                .anyRequest().authenticated();


                http
                        .oauth2Login()
                        .userInfoEndpoint().userService(oauth2UserService)
                        .and()
                        .defaultSuccessUrl("http://192.168.43.19:3000/verification/redirect")
                        .and().logout().logoutSuccessUrl("/").permitAll();

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    public UsernamePasswordAuthenticationFilter authFilter() throws Exception {
        return new JwtEmailPassAuthFilter(authenticationManager());
    }
}
