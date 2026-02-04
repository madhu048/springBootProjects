package com.moviedetails.moviedetails.IService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moviedetails.moviedetails.Entity.UserDetailsEntity;
import com.moviedetails.moviedetails.Repository.UserDetailsEntityRepo;

@Service
public class UserDetailsEntityService implements UserDetailsService
{
    @Autowired
    public UserDetailsEntityRepo userDetailsEntityRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<UserDetailsEntity> userdetails = userDetailsEntityRepo.findByUserName(username);

        if(userdetails.isPresent()){
            UserDetailsEntity user = userdetails.get();
            // Here we have to  convert our UserDetailsEntity to Spring Security  UserDetails object  because 
            // Spring  Security  Works  with  its own UserDetails object not with our DB  Entity object 
            UserDetails springUser =User.withUsername(user.getUserName()).password(user.getUserPassword()).roles(user.getUserRole()).disabled(!user.isEnabled()).build();
            return springUser;
        } else {
            throw  new UsernameNotFoundException("User Not Found With the User: "+username);
        }
        
    }
    
}
