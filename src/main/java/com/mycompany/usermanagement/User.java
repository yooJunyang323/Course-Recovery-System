/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

/**
 *
 * @author gk005
 */
//StudentID,FirstName,LastName,Major,Year,Email
public class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Boolean isActive;
    private String lastLogin;
    
    
    public User(String userID, String firstName, String lastName, String email, String password, String role, Boolean isActive, String lastLogin){
        
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.lastLogin = lastLogin;
    }
    
    public String getUserID(){
        return userID;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getRole(){
        return role;
    }
    
    public Boolean getIsActive(){
        return isActive;
    }
    
    public String getLastLogin(){
        return lastLogin;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }
    
    public void setLastLogin(String lastLogin){
        this.lastLogin = lastLogin;
    }
    
    public String getMajor(){
        return "--";
    }
    
    public String getYear(){
        return "--";
    }
    
    public String toCSVString(){
        return String.join(",",
                userID,
                firstName,
                lastName,
                email,
                password,
                role,
                String.valueOf(isActive),
                lastLogin
        );
    }
    
    public User(User other){
        this.userID = other.userID;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.email = other.email;
        this.password = other.password;
        this.role = other.role;
        this.isActive = other.isActive;
        this.lastLogin = other.lastLogin;
    }

}