package com.moviedetails.moviedetails.CustomException;

public class NoMoviesFoundException extends RuntimeException
{
    public NoMoviesFoundException(String  message){
        super(message);
    }
}
