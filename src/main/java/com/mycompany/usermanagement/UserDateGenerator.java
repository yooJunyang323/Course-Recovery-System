/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

/**
 *
 * @author gk005
 */
public class UserDateGenerator {
    private UserFileReader reader;
    
    public UserDateGenerator(String filepath){
        this.reader = new UserFileReader(filepath);
    }
    
    public String generateuserID(String role){
        String prefix = getPrefix(role);
        
        int count = reader.countUserByPrefix(prefix);
        int newNumber = count;
        
        String formatted = String.format("%03d", newNumber);
        return prefix + formatted;
    }
    
    public static String generatePassword(String userID){
        return userID + "@0987654321";
    }
    
    public String generateEmail(String userID){
        return userID + "@university.edu";
    }
    
    public String generateLastlogin(){
        return "--";
    }
    
    private String getPrefix(String role){
        switch(role){
            case "Student": return "S";
            case "Officer": return "O";
            case "Admin": return "A";
            default:return"X";
        }
    }
}
