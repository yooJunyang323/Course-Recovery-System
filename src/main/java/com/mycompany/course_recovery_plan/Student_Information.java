package com.mycompany.course_recovery_plan;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Student_Information {
    private static final String CSV_FILE_PATH = "C:\\Users\\User\\Documents\\NetBeansProjects\\Course_Recovery_Plan\\student_information.csv"; 
    private static final String DELIMITER = ",";
    
    public String id;
    public String name;
    public String major;
    public String year;
    public String email;
    
    
    public Student_Information() {
        //
    }

    public Student_Information(String id, String FirstName, String LastName, String major, String year, String email) {
        this.id = id;
        this.name = FirstName + LastName;
        this.major = major;
        this.year = year;
        this.email = email;
    }
    
    
    
    public static List<Student_Information> info = new ArrayList<>();
    
    public static void readCsvData(){
        info.clear();
        String line;
        int lineNumber = 0;
        
        try(BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH ))){
            while ((line = br.readLine()) != null) {
                lineNumber ++;
                if (lineNumber == 1) {
                    System.out.println("Skipping Header: " + line);
                    continue; 
                }
                
                String[] values = line.split(DELIMITER); 
                
                System.out.println("\n--- New Record Found ---");
                
                System.out.println("Array Output (readCsvData): " + Arrays.toString(values)); 
                

                
                
                if (values.length >= 4) {
                    try {
                        Student_Information info1 = new Student_Information(
                            values[0].trim(), // ID
                            values[1].trim(), // First Name
                            values[2].trim(), // Last Name
                            values[3].trim(), // major
                            values[4].trim(), // year
                            values[5].trim() //email
                    );
                    
                    // Add object into list
                    info.add(info1);
                    System.out.println("readCsvData: " + info);
                    
                    } catch (NumberFormatException e) {
                        System.err.println("Data Error on line " + lineNumber + ": Failed to parse number or boolean.");
                    }
                    
                } else {
                    System.out.println("values.length < 4");
                }
            }
        } catch (IOException e) {
            
        }
        
    }
    
    
    public static void main(String[] args) {
        readCsvData();
        System.out.println("Total loaded: " + info.size());
    }
    
    
    
    
}
