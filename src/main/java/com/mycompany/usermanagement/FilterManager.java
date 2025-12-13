/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author gk005
 */
public class FilterManager {
    
    private List<User> allUsers;
    private boolean activeOnly = true;
    private String searchText = "";
    private String searchBy = "ID";
    private String roleFilter = "All";
    private String majorFilter = "All";
    private String yearFilter = "All";
    
    public FilterManager(List<User> allUsers){
        this.allUsers = allUsers;
    }
    
    public void setActiveOnly(boolean activeOnly){
        this.activeOnly = activeOnly;
    }
    
    public void SearchText(String searchText){
        this.searchText = searchText == null ? "" : searchText.trim();
    }
    
    public void setsearchBy(String searchBy){
        this.searchBy = searchBy;
    }
    
    public void setRoleFilter(String roleFilter){
        this.roleFilter = roleFilter;
    }
    
    public void setMajorFilter(String majorFilter){
        this.majorFilter = majorFilter;
    }
    
    public void setYearFilter(String yearFilter){
        this.yearFilter = yearFilter;
    }
    
    public List<User> applyFilters(){
        
        String search = searchText.toLowerCase();
        String role = roleFilter;
        String major = majorFilter;
        String year = yearFilter;
        
        return allUsers.stream()
                
                .filter(u -> !activeOnly || u.getIsActive())
                
                .filter(u -> {
                    if(searchText.isEmpty()) return true;
                    
                    if("UserID".equalsIgnoreCase(searchBy)){
                        return u.getUserID().toLowerCase().contains(search);
                    }else{
                        String fullName = u.getFirstName() + " " + u.getLastName();
                        return fullName.toLowerCase().contains(search);
                    }
                })
                
                .filter(u -> "All".equals(role) || u.getRole().equals(role))
                
                .filter(u -> {
                    if(!(u instanceof Student)) return "All".equals(major);
                    Student s = (Student) u;
                    return "All".equals(major) || s.getMajor().equals(major);
                })
                
                .filter(u -> {
                    if (!(u instanceof Student)) return "All".equals(year);
                    Student s = (Student) u;
                    return "All".equals(year) || ("" + s.getYear()).equals(year);
                })
                .collect(Collectors.toList());
                
    }
}
