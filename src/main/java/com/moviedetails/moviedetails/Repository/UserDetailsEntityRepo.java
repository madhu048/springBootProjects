package com.moviedetails.moviedetails.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviedetails.moviedetails.Entity.UserDetailsEntity;
import java.util.Optional;


public interface UserDetailsEntityRepo extends JpaRepository<UserDetailsEntity, Integer>
{
    Optional<UserDetailsEntity> findByUserName(String userName);
}
