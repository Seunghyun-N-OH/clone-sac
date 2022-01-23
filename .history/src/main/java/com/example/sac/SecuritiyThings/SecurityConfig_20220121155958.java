package com.example.sac.SecuritiyThings;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/styles/**", "/imgsrc/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/sitemap/**", "/member/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .loginPage("/member/signin")
                .loginProcessingUrl("/member/signin")
                .defaultSuccessUrl("/")
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
