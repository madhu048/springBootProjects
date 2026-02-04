package com.moviedetails.moviedetails.CustomException;
import java.nio.file.AccessDeniedException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoMoviesFoundException.class)
    public String handleNoMoviesFoundException(NoMoviesFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error";
    }

    // @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    // public String handleRuntimeException(RuntimeException  ex, Model model){
    //     model.addAttribute("errorMessage", ex.getMessage());
    //     model.addAttribute("errorCode","500");
    //     return "error";
    // }

    //  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class) // it may override access denied exception, we already  handling it in config class
    // public String handleRuntimeException(Exception  ex, Model model){            // so in that case we should have only one hadler thats why  we comment this  methode
    //     model.addAttribute("errorMessage", ex.getMessage());
    //     model.addAttribute("errorCode","500");
    //     return "error";
    // }

    // @org.springframework.web.bind.annotation.ExceptionHandler(NoResourceFoundException.class)
    // public String handleNoResourceFoundException(NoResourceFoundException ex,Model model){
    //     model.addAttribute("errorMessage", ex.getMessage());
    //     model.addAttribute("errorCode", "403");
        // return "error";
    // }

    // @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    // public String handleAccessDeniedException(AccessDeniedException ex, Model model){

    //     model.addAttribute("errorMessage", "Access Denied! You do not have permission to access this page.");
    //     model.addAttribute("errorCode", "401");
    //     return "error";
    // }
    
}
