package com.example.sac.SecuritiyThings.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public SecurityConfig(CustomAuthenticationFailureHandler cfh) {
        this.cfh = cfh;
    }

    private final CustomAuthenticationFailureHandler cfh;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/styles/**", "/imgsrc/**", "/javascript/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/member/mypage/**").hasRole("USER")
                .antMatchers("/member/signup").permitAll()
                .antMatchers("/member/signin", "/member/signup/**").anonymous()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .loginPage("/member/signin")
                .loginProcessingUrl("/member/signin")
                .defaultSuccessUrl("/")
                .failureHandler(cfh)
                .and()
                .logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
