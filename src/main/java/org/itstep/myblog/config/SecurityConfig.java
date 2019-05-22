package org.itstep.myblog.config;

import org.itstep.myblog.auth.BlogUserDetailsService;
import org.itstep.myblog.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests ()
                    .antMatchers ("/**").permitAll ()
//        .and().csrf().ignoringAntMatchers ("/api/**")
        .and().csrf().disable ()
//                    .antMatchers ("/images/*").permitAll ()
//                    .antMatchers ("/register").permitAll ()
//                    .antMatchers("/css/**").permitAll()
//                    .anyRequest().authenticated()
//                .and ()
//                    .formLogin ()
//                        .loginPage ("/login").permitAll ()
//                .and ()
//                    .logout ()
//                        .logoutSuccessUrl ("/")
//                        .invalidateHttpSession (true)
        ;
    }

    @Bean
    public UserDetailsService userDetailsService(UsersRepository repository) {
        return new BlogUserDetailsService (repository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ();
    }

}
