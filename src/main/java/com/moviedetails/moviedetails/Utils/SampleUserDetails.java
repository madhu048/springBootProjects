package com.moviedetails.moviedetails.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.moviedetails.moviedetails.Entity.UserDetailsEntity;
import com.moviedetails.moviedetails.Repository.UserDetailsEntityRepo;

import jakarta.annotation.PostConstruct;

@Component
public class SampleUserDetails {
    
    @Autowired
    public UserDetailsEntityRepo repo;

    @Autowired
    public PasswordEncoder encoder;

    @PostConstruct
    public void saveUserDetails(){
        if(!repo.findByUserName("madhu").isPresent()){
            UserDetailsEntity user = new UserDetailsEntity();
            user.setUserName("madhu");
            user.setUserPassword(encoder.encode("madhu@123"));
            user.setUserRole("ADMIN");

            repo.save(user);
        };
        if(!repo.findByUserName("Kiran").isPresent()){
            UserDetailsEntity user = new UserDetailsEntity();
            user.setUserName("kiran");
            user.setUserPassword(encoder.encode("kiran@123"));
            user.setUserRole("USER");
            repo.save(user);
        }
        
    }
}
