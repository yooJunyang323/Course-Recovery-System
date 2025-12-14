package com.mycompany.course_recovery_plan;


import com.mycompany.course_recovery_plan.Recovery_plan;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class Course_Information {
    private static final String CSV_FILE_PATH = "C:\\Users\\User\\Documents\\NetBeansProjects\\Course_Recovery_Plan\\course_information.csv"; 
    private static final String DELIMITER = ",";
    
    public static List<Course_Information> courseInfo = new ArrayList<>();
    public String courseCode;
    public String tittle;
    public String major;
    public int semester;
    public int credit;

    public Course_Information(String courseCode, String tittle, String major, int semester, int credit) {
        this.courseCode = courseCode;
        this.tittle = tittle;
        this.major = major;
        this.semester = semester;
        this.credit = credit;
    }
    
    
    
    public static void readCsvData(){
        courseInfo.clear();
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
                        Course_Information ci = new Course_Information(
                            values[0].trim(), // courseCode
                            values[1].trim(), // tittle
                            values[2].trim(), // major
                            Integer.parseInt(values[3].trim()), // semester
                            Integer.parseInt(values[4].trim()) //credit hours
                    );
                    
                    // Add object into list
                    courseInfo.add(ci);
                    System.out.println("readCsvData: " + ci);
                    
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
    
}
