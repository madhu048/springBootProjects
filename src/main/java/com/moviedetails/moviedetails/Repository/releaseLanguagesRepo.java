package com.moviedetails.moviedetails.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviedetails.moviedetails.Entity.ReleaseLanguages;

public interface releaseLanguagesRepo extends JpaRepository<ReleaseLanguages, Integer>
{
    
}
