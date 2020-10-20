package com.hew.basicframework.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 14:14
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LoginUserDetailsService userDetailsService;
    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    LoginAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    LoginAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize ->
        {
            try {
                authorize.antMatchers("/favicon.ico", "/swagger-ui/*", "/swagger-resources/**", "/webjars/**","/v3/api-docs").permitAll()
                        .anyRequest().authenticated()
                        .and().formLogin()
                        .loginProcessingUrl("/login")
                        .failureHandler(authenticationFailureHandler())
                        .successHandler(authenticationSuccessHandler())
                        .and().cors().disable().csrf().disable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //禁用缓存
        http.headers().cacheControl().disable();
        //不使用session，使用jwt，所以不创建session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //将JWT过滤器放在用户名密码过滤器前面
        http.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean(value = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return this.authenticationFailureHandler;
    }

    @Bean
    public LoginAuthenticationSuccessHandler authenticationSuccessHandler() {
        return this.authenticationSuccessHandler;
    }

}
