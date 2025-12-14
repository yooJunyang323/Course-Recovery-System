/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.course_recovery_plan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Recovery_plan {

    
    private static final String CSV_FILE_PATH = "C:\\Users\\User\\Documents\\NetBeansProjects\\Course_Recovery_Plan\\course_recovery.csv"; 
    private static final String DELIMITER = ",";
    
    public String studentID;
    public String courseCode;
    public int weekNo;
    public String task;
    public boolean status;
    public String recommendation;

    public Recovery_plan() {}
    
    public Recovery_plan(String studentID, String courseCode, int weekNo, String task,boolean status, String recommendation) {
        this.studentID = studentID;
        this.courseCode = courseCode;
        this.weekNo = weekNo;
        this.task = task;
        this.status = status;
        this.recommendation = recommendation;
    }
    
    public static List<Recovery_plan> plan = new ArrayList<>();
    
    public static void main(String[] args) {
        readCsvData();
        System.out.println("Total loaded: " + plan.size());
    }
    
    public static void readCsvData(){
        plan.clear();
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
                
                String recommendation = "";
                if (values.length > 5 && values[5] != null) {
                    recommendation = values[5].trim();
                }
                
                
                if (values.length >= 4) {
                    try {
                        Recovery_plan rp = new Recovery_plan(
                            values[0].trim(), // StudentID
                            values[1].trim(), // CourseCode
                            Integer.parseInt(values[2].trim()), // WeekNo
                            values[3].trim(), // Task
                            Boolean.parseBoolean(values[4].trim()), //Status
                            recommendation // Recommendation
                    );
                    
                    // Add object into list
                    plan.add(rp);
                    System.out.println("readCsvData: " + rp);
                    
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
    
    public static void saveCsvData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
            writer.write("Student ID,Course Code,Week,Task,Status,Recommendation");
            writer.newLine();

            for (Recovery_plan rp : plan) {
                String line = String.format("%s,%s,%d,%s,%b,%s",
                    rp.studentID,
                    rp.courseCode,
                    rp.weekNo,
                    rp.task,
                    rp.status,
                    rp.recommendation 
                );
                writer.write(line);
                writer.newLine();
            }
            System.out.println("CSV file updated successfully.");

        } catch (IOException e) {
            System.err.println("Error saving CSV: " + e.getMessage());
        }
    }
    
    
}
