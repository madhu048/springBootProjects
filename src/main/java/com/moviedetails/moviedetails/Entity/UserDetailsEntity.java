package com.moviedetails.moviedetails.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserDetailsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_role")
    private String userRole;
    @Column(name = "user_mail")
    private String userMail;
    @Column(name = "user_contact", length = 15)
    private String userPhoneNumber;
    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "user_gender")
    private String userGender;
    @Column(name = "user_maritalstatus")
    private String userMaritalStatus;
    @Column(name = "user-profile")
    private String userProfileImage;

    @Column(name= "enabled")
    private boolean enabled=true;
}
