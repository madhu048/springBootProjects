package com.moviedetails.moviedetails.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviedetails.moviedetails.Entity.MovieDetails;
import com.moviedetails.moviedetails.Entity.ReleaseLanguages;
import com.moviedetails.moviedetails.Entity.UserDetailsEntity;
import com.moviedetails.moviedetails.IService.ServiceImpl;
import com.moviedetails.moviedetails.Repository.UserDetailsEntityRepo;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    public ServiceImpl serviceImpl;
    
    @Autowired
    public UserDetailsEntityRepo userRepo;

    @Autowired
    public PasswordEncoder encoder;

    @GetMapping("/login")
    public String loginPage(){
        
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(value = "failed_message", required = false)String failed_message,
                                @RequestParam(value = "failed_message2", required = false)String failed_message2, Model model){

        model.addAttribute("failed_message", failed_message);
        model.addAttribute("failed_message2", failed_message2);
        return "register";

    }

    @PostMapping("/saveuser")
    public String saveUser(@ModelAttribute UserDetailsEntity user, @RequestParam("ProfileImage") MultipartFile file, RedirectAttributes attributes, Model model) throws IOException{
        try {
                 // Validate file is not empty
                if(file.isEmpty()){
                    model.addAttribute("failed_message"," Please upload an image.");
                    return "registrationfailed";
                }
                // Validate file type (only jpg/png allowed)
                if(!file.getContentType().equals("image/png") &&!file.getContentType().equals("image/jpeg")){
                    model.addAttribute("failed_message"," Only JPG and PNG images are allowed.");
                    return "registrationfailed";
                }
                // Validate file size (only below 2mb)
                if(file.getSize()>= 2 * 1024 * 1024){
                    model.addAttribute("failed_message"," File size should be lessthan 2mb.");
                    return "registrationfailed";
                }
        
                String userName = user.getUserName();
                if ((!userRepo.findByUserName(userName).isPresent())) {

                    // Password Encryption
                    String rawPassword = user.getUserPassword();
                    String encodedpassword =encoder.encode(rawPassword);
                    user.setUserPassword(encodedpassword);

                    // Saving the  user  in  db
                    UserDetailsEntity savedUser1 = userRepo.save(user);

                    // IMAGE UPLOAD LOGIC
                    String uploadFolder = "C:/Users/geram/OneDrive/Desktop/SpringBoot/ProfileImages/";            // Storing folder name a variable
                    Path uploadPath = Paths.get(uploadFolder);   // It  will create a object that points to the location/folder/path that we are going to  create.
                    String extension = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename()); // Extracting the original Extension  of the uploaded image.
                    String fileName = savedUser1.getUserId()+"."+extension; // Creating the  file  name with  user  id and image extension.
                    if(!Files.exists(uploadPath)){
                        Files.createDirectories(uploadPath);      // It will create the folder with given folder name  by  using pointer object.
                    }
                    Path filePath = uploadPath.resolve(fileName); // resolve will  add the filename into uploads folder
                    file.transferTo(filePath.toFile());           // TransferTo method needs File object not the Paht so we have converted Path to File.
                    // SAVE FILE NAME IN DB
                    savedUser1.setUserProfileImage(fileName);

                    // Extracting the  saved user from db and  displaying in webpage.
                    Optional<UserDetailsEntity> savedUser = userRepo.findByUserName(userName);
                    if(savedUser.isPresent()){
                        UserDetailsEntity dbUser = savedUser.get();
                        String dbuserName = dbUser.getUserName();
                        String dbuserRole = dbUser.getUserRole();
                        model.addAttribute("dbuserName",dbuserName);
                        model.addAttribute("dbuserRole", dbuserRole);
                        return "registerSuccess";
                    }else{
                        // attributes.addAttribute("failed_message2", userName +"Failed to store in database.");
                        // return "redirect:/movie/register";

                        model.addAttribute("failed_message2", userName +" Failed to store in database.");
                        return "registrationfailed";
                    }
                    
                }else{
                    // attributes.addAttribute("failed_message", userName +"already existed in database.");
                    // return "redirect:/movie/register";

                    model.addAttribute("failed_message", userName +" already existed in database.");
                    return "registrationfailed";
                }
        } catch (Exception e) {
            model.addAttribute("failed_message", "Error uploading image: " + e.getMessage());
            return "registrationfailed";
        } 
    }
     
    @GetMapping("/info")
    public String showMovieInfo(@RequestParam(value = "Status", required = false) String status, Model model){
        
        if(status != null){
            model.addAttribute("Status", status);
        }; 
        return  "movieinfo";
    }

    @PostMapping("/add")
    public String AddNewMovie(@ModelAttribute MovieDetails movieDetails, 
                                @RequestParam(required = false, name = "languageNames") String[] languageNames,
                                RedirectAttributes attributes){
        // We will get all selected languages from  the  form  as an  string array, so we  can't directly set this  string  array to  the releaseLangages field of MovieDetails entity,
        // so first we need  to  create  a empty list of ReleaseLanguages entity  then we  will  create object  of ReleaseLanguages  entity then we will  add this object  to the list,
        // but  the object must set with ReleaseLanguage and  MovieDetails fields before adding to the list,
        // if user selects multiple languages then we need to do this process  for  each selected  language,that's why  we are using  loop to  iterate  through  the  string array,
        // finally we will set the list of  Releaselanguages  to  the  MovieDetails entity manually and then  we will pass this  entity to the  service layer method.                            
        List<ReleaseLanguages> langs = new ArrayList<>(); // Creating  a  list to  hold  ReleaseLanguages entities
        if(languageNames != null){
            for(String language : languageNames){
                ReleaseLanguages  rl = new ReleaseLanguages(); // Creating ReleaseLanguages entity for each selected language
                rl.setReleageLanguage(language);
                rl.setMovieDetails(movieDetails);
                langs.add(rl); // Adding ReleaseLanguages  entity  to the  list
            }
        };
        movieDetails.setReleaseLanguages(langs); // Setting the list of ReleaseLanguages to MovieDetails entity  manually
        String movieName = serviceImpl.addMovieDetails((movieDetails));
        attributes.addAttribute("Status",movieName);
        return "redirect:/movie/info";
    }

    @PostMapping("/update")
    public String  UpdateMovie(@ModelAttribute MovieDetails movieDetails,
                                @RequestParam(required=false, name="languageNames") String [] languageNames,
                                RedirectAttributes attributes)
    {
        MovieDetails updatedMovie = serviceImpl.updateMovieDetails(movieDetails, languageNames);
        String name = updatedMovie.getMovieName()+" movie updated successfully";
        attributes.addAttribute("movieName",name);
        return  "redirect:/movie/all";
    }

    @PostMapping("/edit/{movieId}")
    public String editMovie(@PathVariable Integer movieId, Model model){
        MovieDetails  movieDetails = serviceImpl.getMovieDetails(movieId);
        model.addAttribute("movieDetails", movieDetails);
        return "editMovie";
    }

    @PostMapping("/delete/{movieId}")
    public String DeleteMovie(@PathVariable Integer movieId, RedirectAttributes attributes){
        String res = serviceImpl.deleteMovieDetails(movieId);
        attributes.addAttribute("Result",res);
        return "redirect:/movie/all";
    }
    
    @GetMapping("/all")
     public String showAllMovies(Model model, @RequestParam(value = "Result", required = false) String result,
                                                @RequestParam(value ="movieName",  required = false) String moviename){
       List<MovieDetails> movieList = serviceImpl.listAllMovies();
       model.addAttribute("movieList", movieList);
       model.addAttribute("result", result);
        model.addAttribute("moviename", moviename);
        return "admin";
     }

     @GetMapping("/access-denied")
     public String accessDeniedPage(Model model){
        model.addAttribute("errorMessage", "Access Denied! You do not have permission to access this page.");
        model.addAttribute("errorCode", "403");
        return "error";
     }
}
