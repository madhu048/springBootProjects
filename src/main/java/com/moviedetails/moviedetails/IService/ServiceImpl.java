package com.moviedetails.moviedetails.IService;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviedetails.moviedetails.CustomException.NoMoviesFoundException;
import com.moviedetails.moviedetails.Entity.MovieDetails;
import com.moviedetails.moviedetails.Entity.ReleaseLanguages;
import com.moviedetails.moviedetails.Repository.movieDetailsRepo;
import com.moviedetails.moviedetails.Repository.releaseLanguagesRepo;

@Service
public class ServiceImpl implements ImovieDetailsService
{
    @Autowired
    public movieDetailsRepo MDrepo;

    @Autowired
    public  releaseLanguagesRepo RLrepo;

    public MovieDetails getMovieDetails(Integer movieId) {
        Optional<MovieDetails> res = MDrepo.findById(movieId);
        if(res.isPresent()){
            return  res.get();
        }else{
            throw new RuntimeException("Movie not found with  id:  "+movieId);
        }
    }

    public String addMovieDetails(MovieDetails movieDetails) {

        MDrepo.save(movieDetails);
        String name = movieDetails.getMovieName();
        return  "Movie added  successfully: "+name;
    }

    public MovieDetails updateMovieDetails(MovieDetails movieDetails, String [] languageNames) { // From the  form we will get the languages as string array  not as list
                                                                                                // that why we are adding string array as parameter
        Integer movieId = movieDetails.getMovieId();
        Optional<MovieDetails> existingMovie = MDrepo.findById(movieId);
        if(existingMovie.isPresent()){
            MovieDetails updateMovie = existingMovie.get();
            updateMovie.setMovieName(movieDetails.getMovieName());
            updateMovie.setMovieHero(movieDetails.getMovieHero());
            updateMovie.setMovieDirector(movieDetails.getMovieDirector());
            updateMovie.setMovieBudget(movieDetails.getMovieBudget());

            List<ReleaseLanguages> langs = updateMovie.getReleaseLanguages(); // Getting  the existing list of  ReleaseLanguages
            langs.clear();                                                      // clearing  the  existing list so that we can add the  updated languages
            if(languageNames != null){
                for(String language : languageNames){
                    ReleaseLanguages rl = new ReleaseLanguages();
                    rl.setReleageLanguage(language);
                    rl.setMovieDetails(updateMovie);
                    langs.add(rl);
                }
            };

            updateMovie.setReleaseLanguages(langs);
            MDrepo.save(updateMovie);
            return updateMovie;
        }else{
            throw new RuntimeException("Movie not found with id: "+movieId);
        }
    }

    public String deleteMovieDetails(Integer movieId) {

        Optional<MovieDetails> res = MDrepo.findById(movieId);
        if(res.isPresent()){
            MDrepo.deleteById(movieId);
            return "Movie deleted successfully : "+ res.get().getMovieName();
        }else{
            throw new RuntimeException("Movie not found with id: "+movieId);
        }
    }

    public List<MovieDetails> listAllMovies() {

        List<MovieDetails> movies = MDrepo.findAll();
        if(!movies.isEmpty()){
            return movies;
        }else{
            throw new NoMoviesFoundException("No movies found in the database.");
        }
    }
    
}
