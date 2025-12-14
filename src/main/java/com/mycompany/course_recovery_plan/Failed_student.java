    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.course_recovery_plan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Failed_student {
    private static final String CSV_FILE_PATH = "C:\\Users\\User\\Documents\\NetBeansProjects\\Course_Recovery_Plan\\student_assessment_information.csv"; 
    private static final String DELIMITER = ",";
    
    public String studentID;
    public String courseCode;
    public int attemptNo;
    public int examGrade;
    public int assignmentGrade;
    public boolean recoveryPlan;
    
    public Failed_student() {}

    public Failed_student(String studentID, String courseCode, int attemptNo, int examGrade, int assignmentGrade, boolean recoveryPlan) {
        this.studentID = studentID;
        this.courseCode = courseCode;
        this.attemptNo = attemptNo;
        this.examGrade = examGrade;
        this.assignmentGrade = assignmentGrade;
        this.recoveryPlan = recoveryPlan;
    }
    
    public static List<Failed_student> students = new ArrayList<>();
    
    public static void main(String[] args) {
        readCsvData();
        System.out.println("Total loaded: " + students.size());
    }
    
    public static void readCsvData() {
        students.clear();
        String line;
        int lineNumber = 0;
        System.out.println("Starting to read CSV file: " + CSV_FILE_PATH);

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            
            while ((line = br.readLine()) != null) {
                lineNumber ++;
                if (lineNumber == 1) {
                    System.out.println("Skipping Header: " + line);
                    continue; 
                }
                
                String[] values = line.split(DELIMITER); 
                
                System.out.println("\n--- New Record Found ---");
                
                System.out.println("Array Output: " + Arrays.toString(values)); 
                
                
                if (values.length >= 8) {
                    try {
                    // Create object using the corrected indices and constructor
                    Failed_student fs = new Failed_student(
                        values[0].trim(), // StudentID
                        values[1].trim(), // CourseCode
                        Integer.parseInt(values[2].trim()), // Attempt_no
                        Integer.parseInt(values[3].trim()), // ExamGrade
                        Integer.parseInt(values[4].trim()), // AssignmentGrade
                        Boolean.parseBoolean(values[7].trim()) // Has_recovery
                    );
                    
                    // Add object into list
                    students.add(fs);
                    
                } catch (NumberFormatException e) {
                    System.err.println("Data Error on line " + lineNumber + ": Failed to parse number or boolean.");
                }
                    
                }
            }
            
        } catch (IOException e) {
            // This catches file-not-found errors or other input/output issues
            System.err.println("\nERROR: Could not read the CSV file.");
            System.err.println("Please check the file path: " + CSV_FILE_PATH);
            System.err.println("Details: " + e.getMessage());
        }    
    }
    
    public static void updateStatus(String studentID, String courseCode, boolean newStatus){
        String line;
        int lineNumber = 0;
        List<String> updatedLines = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))){
            
            while ((line=br.readLine()) != null){
                lineNumber ++;
                if (lineNumber ==1){
                    updatedLines.add(line);
                    continue;
                }
                
                String[] values = line.split(DELIMITER); 
                
                if (values[0].trim().equals(studentID) && values[1].trim().equals(courseCode)){
                    System.out.println("Match line in failed_student: "+ line);
                    values[7] = String.valueOf(newStatus);
                    line = String.join(DELIMITER,values);
                    
                }
                
                updatedLines.add(line);
                System.out.println("Updated Lines: "+ updatedLines);
                System.out.println("Status: " + newStatus);
                
                
            }
            
            try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
                    for (String l : updatedLines) {
                        pw.println(l);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("CSV updated successfully.");
            
        } catch (IOException e) {
            System.out.println("Failed update Status");
        }
    }
}
