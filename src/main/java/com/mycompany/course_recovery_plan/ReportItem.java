package com.mycompany.course_recovery_plan;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class ReportItem {
    String id;
    String name;
    String major;
    String courseCode;
    String courseName;
    int semester;
    int credit;
    int attemptNum;

    public ReportItem(String id, String name, String major, String courseCode, 
                      String courseName, int semester, int credit, int attemptNum) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semester = semester;
        this.credit = credit;
        this.attemptNum = attemptNum;
    }
}