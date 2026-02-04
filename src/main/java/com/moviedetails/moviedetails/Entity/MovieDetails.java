package com.moviedetails.moviedetails.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Movie_Details")
public class MovieDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="movie_id")
    public Integer movieId;
    @Column(name="movie_name")
    public String movieName;
    @Column(name="movie_director")
    public String movieDirector;
    @Column(name="movie_hero")
    public String movieHero;
    @Column(name="movie_budget")
    public long movieBudget;

    @OneToMany(mappedBy = "movieDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ReleaseLanguages> releaseLanguages;
    
}
