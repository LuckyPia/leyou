package com.leyou.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.common.vo.RespBean;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userServic;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider provider = new MyAuthenticationProvider();
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(userServic);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/db/**").hasRole("user")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    resp.setStatus(200);
                    RespBean respBean = RespBean.ok("登录成功!");
                    ObjectMapper om = new ObjectMapper();
                    PrintWriter out = resp.getWriter();
                    out.write(om.writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    RespBean respBean = null;
                    if (e instanceof BadCredentialsException ||
                            e instanceof UsernameNotFoundException) {
                        respBean = RespBean.error("账户名或者密码输入错误!");
                    } else if (e instanceof LockedException) {
                        respBean = RespBean.error("账户被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        respBean = RespBean.error("密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        respBean = RespBean.error("账户过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        respBean = RespBean.error("账户被禁用，请联系管理员!");
                    } else {
                        respBean = RespBean.error("登录失败!");
                    }
                    resp.setStatus(401);
                    ObjectMapper om = new ObjectMapper();
                    PrintWriter out = resp.getWriter();
                    out.write(om.writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        resp.setStatus(200);
                        RespBean respBean = RespBean.ok("注销成功!");
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and().csrf().disable();
    }
}
