package com.moviedetails.moviedetails.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Release_Languages")
public class ReleaseLanguages {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="RId")
    public Integer releaseId;
    @Column(name="Rlanguage")
    public String releageLanguage;
    
    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    public MovieDetails movieDetails;
}
