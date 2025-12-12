/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

/**
 *
 * @author gk005
 */
public class Admin extends User{
    public Admin(String userID, String firstName, String lastName, String email,
                 String password, String role, Boolean isActive, String lastLogin) {
        super(userID, firstName, lastName, email, password, "Admin", isActive, lastLogin);
    }
    
    @Override
    public String toCSVString(){
        return super.toCSVString();
    }
    
    public Admin(User other){
        super(other);
    }
}
