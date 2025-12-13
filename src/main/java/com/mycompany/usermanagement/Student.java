/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

/**
 *
 * @author gk005
 */
public class Student extends User {
    private String major;
    private String year;
    
    public Student(String userID, String firstName, String lastName, String email, String password, String role, Boolean isActive, String lastLogin, String major, String year){
        super(userID, firstName, lastName, email, password, "Student", isActive, lastLogin);
        
        this.major = major;
        this.year = year;
    }

    @Override
    public String getMajor(){
        return major;
    }

    @Override
    public String getYear(){
        return year;
    }
    
    public void setMajor(String major){
        this.major = major;
    }
    
    public void setYear(String year){
        this.year = year;
    }
    @Override
    public String toCSVString(){
        return super.toCSVString() + "," +
                String.join(",",major,year);
    }
    
    public Student(User other){
        super(other);
        
        if (other instanceof Student s) {
            this.major = s.getMajor();
            this.year = s.getYear();
        }else{
            this.major = "";
            this.year = "";
        }
        
    }
    
}