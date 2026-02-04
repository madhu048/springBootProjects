package com.moviedetails.moviedetails.IService;

import java.util.List;

import com.moviedetails.moviedetails.Entity.MovieDetails;

public interface ImovieDetailsService {
    
    MovieDetails getMovieDetails(Integer movieId);
    String addMovieDetails(MovieDetails movieDetails);
    MovieDetails updateMovieDetails(MovieDetails movieDetails, String [] languageNames);
    String deleteMovieDetails(Integer movieId);
    List<MovieDetails> listAllMovies();
}
