/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

/**
 *
 * @author gk005
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.lang.module.InvalidModuleDescriptorException;
//import java.nio.file.*;
import java.util.*;

public class UserFileReader {
    
    private final String filePath;
    
    public UserFileReader(String filePath){
        this.filePath = filePath;
    }
    
    public User findUserByID(String inputUserID){
        BufferedReader br = null;
        
        try{
            br = new BufferedReader(new FileReader(filePath));
            
            String line;
            
            while ((line = br.readLine()) != null){
                
                String[] values = line.split(",");
                
                String userID = values[0];
                String firstName = values[1];
                String lastName = values[2];
                String email = values[3];
                String password = values[4];
                String role = values[5];
                Boolean isActive = values[6].equalsIgnoreCase("true");
                String lastLogin = values[7];
                
                if (userID.equalsIgnoreCase(inputUserID)){
                    
                    if(role.equalsIgnoreCase("Student")){
                        String major = values[8];
                        String year = values[9];
                        return new Student(userID,firstName,lastName,email,password,role,isActive,lastLogin,major,year);
                    }else if (role.equalsIgnoreCase("Admin")){
                        return new Admin(userID,firstName,lastName,email,password,"Admin",isActive,lastLogin);
                    }else if(role.equalsIgnoreCase("Officer")){
                        return new Officer(userID, firstName, lastName,email,password,"Officer",isActive,lastLogin);
                    }
                }
                
            }
        } catch (IOException e) {
            System.out.println("can not open file");
        }
        
        return null;
    }
    
    public User findUserByEmail(String inputEmail){
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                String userID = values[0];
                String firstName = values[1];
                String lastName = values[2];
                String email = values[3];
                String password = values[4];
                String role = values[5];
                Boolean isActive = values[6].equalsIgnoreCase("true");
                String lastLogin = values[7];

                if (email.equalsIgnoreCase(inputEmail)) {
                    if (role.equalsIgnoreCase("Student")) {
                        String major = values[8];
                        String year = values[9];
                        return new Student(userID, firstName, lastName, email, password, role, isActive, lastLogin, major, year);
                    } else if (role.equalsIgnoreCase("Admin")) {
                        return new Admin(userID, firstName, lastName, email, password, "Admin", isActive, lastLogin);
                    } else if (role.equalsIgnoreCase("Officer")) {
                        return new Officer(userID, firstName, lastName, email, password, "Officer", isActive, lastLogin);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("can not open file");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    
    public int countUserByPrefix(String prefix){
        int count = 0;
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;
            
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");
                if(values.length > 0){
                    String userID = values[0];
                    if (userID.startsWith(prefix)) {
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            
        }
        return count;
    }
    
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            br.readLine();
            
            while((line = br.readLine()) != null){
                
                String[] values = line.split(",");
                
                if (values.length < 7) continue;
                
                String userID = values[0];
                String firstName = values[1];
                String lastName = values[2];
                String email = values[3];
                String password = values[4];
                String role = values[5];
                Boolean isActive = values[6].equalsIgnoreCase("true");
                String lastLogin = values[7];
                
                User user;
                
                switch(role){
                    case "Student":
                        String major = values.length >8 ? values[8] : "";
                        String year = values.length >9 ? values[9] : "";
                        
                        user = new Student(userID, firstName, lastName, email, password, role, isActive, lastLogin, major, year);
                        break;
                        
                    case "Officer":
                        user = new Officer(userID, firstName, lastName, email, password, role, isActive, lastLogin);
                        break;
                        
                    case "Admin" :
                        user = new Admin(userID, firstName, lastName, email, password, role, isActive, lastLogin);
                        break;
                        
                    default:
                        user = new User(userID, firstName, lastName, email, password, role, isActive, lastLogin);
                        break;
                }
                
                users.add(user);
            }
        }catch(IOException e){
            
        }
        return users;
    }
}
