package com.moviedetails.moviedetails.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviedetails.moviedetails.Entity.MovieDetails;

public interface movieDetailsRepo extends JpaRepository<MovieDetails, Integer>
{
 
}
