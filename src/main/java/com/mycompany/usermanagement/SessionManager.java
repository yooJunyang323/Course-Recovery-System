/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

/**
 *
 * @author gk005
 */
public class SessionManager {
    private static User loggedUser;
    
    public static void setLoggedUser(User user){
        loggedUser = user;
    }
    
    public static User getLoggedUser(){
        return loggedUser;
    }
    
    public static void clear(){
        loggedUser = null;
    }
}
