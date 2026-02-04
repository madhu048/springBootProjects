package com.moviedetails.moviedetails.ConfigClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

import com.moviedetails.moviedetails.IService.UserDetailsEntityService;

@Configuration
public class webConfig {

    @Autowired
    public UserDetailsEntityService userDetailsEntityService;    
    
    @Bean
    public DefaultSecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(t -> t.requestMatchers("/movie/login","/doLogin","/movie/access-denied","/movie/register","/movie/saveuser").permitAll()
                                         .requestMatchers("/movie/info","/movie/all","/movie/info/**","/movie/all/**").hasAnyRole("USER","ADMIN")
                                         .requestMatchers("/movie/add","/movie/edit/**","/movie/delete/**","/movie/update").hasRole("ADMIN")
                                         .anyRequest().authenticated())
            .formLogin(f -> f.loginPage("/movie/login")
                             .loginProcessingUrl("/doLogin")
                             .defaultSuccessUrl("/movie/info",true)
                             .permitAll())
            .logout(l->l
                .logoutUrl("/logout")               // logout URL it is default no  need add in controller
                .logoutSuccessUrl("/movie/login")   // redirect after logout
                .invalidateHttpSession(true)        // remove session
                .clearAuthentication(true)          // clear security context
                .deleteCookies("JSESSIONID")        // delete cookie
                .permitAll()
            )     
            .exceptionHandling(ex -> ex.accessDeniedPage("/movie/access-denied"))
            .sessionManagement(session ->session
                .invalidSessionUrl("/movie/login?session=invalid")  // ✅ Redirect when session expires
                .maximumSessions(1)                     // ✅ Allow only ONE active session per user
                .maxSessionsPreventsLogin(false)        // ✅ New login kills old session
            );
        return http.build();                  
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity  http, PasswordEncoder passwordEncoder) throws Exception{

        // Here we have to provide spring userdetails object/class and  passwordencoder object to authenticationManagerBuilder 
        // then the  builder will create an AthenticationManager and this manager will do compar username and password

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsEntityService).passwordEncoder(passwordEncoder);
        return builder.build();

    }
    
}
