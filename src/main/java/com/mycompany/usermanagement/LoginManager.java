/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

/**
 *
 * @author gk005
 */

public class LoginManager {
    
    private UserFileReader fileReader;
    
    public LoginManager(UserFileReader fileReader){
        this.fileReader = fileReader;
    }
    
    public User login(String inputID, String inputPassword){
        User user = fileReader.findUserByID(inputID);
        
        if (user == null){
            return null;
        }
        
        if (user.getPassword().equals(inputPassword) && user.getIsActive()){
            updateLastLogin(user);
            return user;
        }
        return null;
    }
    
    private void updateLastLogin(User user){
        String timeStamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        user.setLastLogin(timeStamp);
        
        UserFileWriter writer = new UserFileWriter("student_information.csv");
        writer.updateUserByID(user.getUserID(), user);
    }

}
