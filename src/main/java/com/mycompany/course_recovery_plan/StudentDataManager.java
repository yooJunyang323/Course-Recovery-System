package com.mycompany.course_recovery_plan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentDataManager {

    private final String studentFile;
    private final String assessmentFile;
    private final String courseFile;

    public StudentDataManager(String studentFile, String assessmentFile, String courseFile) {
        this.studentFile = studentFile;
        this.assessmentFile = assessmentFile;
        this.courseFile = courseFile;
    }

    // Load all ineligible students
    public List<Object[]> loadIneligibleStudents() {
        List<Object[]> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(studentFile))) {

            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                String[] rowData = line.split(",");

                if (rowData.length >= 6) {

                    String studentID = rowData[0].trim();
                    String fullName = rowData[1].trim() + " " + rowData[2].trim();
                    String major = rowData[3].trim();
                    String year = rowData[4].trim();

                    List<String[]> grades = getStudentGrades(studentID);

                    if (grades.isEmpty()) continue;

                    double totalPoints = 0;
                    int totalCredits = 0;
                    int failedCourses = 0;

                    for (String[] course : grades) {
                        int credit = getCredit(course[0]);
                        double gradePoint = Double.parseDouble(course[1]);

                        totalCredits += credit;
                        totalPoints += gradePoint * credit;

                        if (gradePoint < 2.0) {
                            failedCourses++;
                        }
                    }

                    double cgpa = totalCredits == 0 ? 0 : totalPoints / totalCredits;
                    
                    if (cgpa > 2.0 && failedCourses <= 3) continue;

                    result.add(new Object[]{
                            studentID, fullName, major, year,
                            String.format("%.2f", cgpa), "Not Eligible"
                    });
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading student file: " + e.getMessage());
        }

        return result;
    }

    // Fetch all grades for one student
    public List<String[]> getStudentGrades(String studentID) {
        List<String[]> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(assessmentFile))) {

            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 8) continue;

                if (!data[0].trim().equalsIgnoreCase(studentID)) continue;

                String courseCode = data[1].trim();
                int attempt = Integer.parseInt(data[2].trim());
                double exam = Double.parseDouble(data[3].trim());
                double assignment = Double.parseDouble(data[4].trim());
                if (attempt > 1) {
                    exam = (exam >= 50) ? 50 : exam;
                    assignment = (assignment >= 50) ? 50 : assignment;
                }
                int[] weight = getWeight(courseCode);
                double score = (exam * weight[0] / 100.0) + (assignment * weight[1] / 100.0);
                double gpa = convertToGradePoint(score);

                result.add(new String[]{
                        courseCode,
                        String.format("%.2f", gpa),
                        String.format("%.0f", exam),
                        String.format("%.0f", assignment),
                        Integer.toString(attempt)
                });
            }

        } catch (IOException e) {
            System.err.println("Error reading assessment file: " + e.getMessage());
        }

        return result;
    }

    public int[] getWeight(String courseId) {
        try (BufferedReader br = new BufferedReader(new FileReader(courseFile))) {

            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {

                if (first) {
                    first = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 7) continue;

                if (!data[0].trim().equalsIgnoreCase(courseId)) continue;

                return new int[]{
                        Integer.parseInt(data[5].trim()),
                        Integer.parseInt(data[6].trim())
                };
            }

        } catch (IOException e) {
            System.err.println("Error reading course file: " + e.getMessage());
        }

        return new int[]{70, 30};
    }

    public int getCredit(String courseId) {
        try (BufferedReader br = new BufferedReader(new FileReader(courseFile))) {

            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {

                if (first) {
                    first = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 7) continue;

                if (!data[0].trim().equalsIgnoreCase(courseId)) continue;

                return Integer.parseInt(data[2].trim());
            }

        } catch (IOException e) {
            System.err.println("Error reading course file: " + e.getMessage());
        }

        return 0;
    }

    public double convertToGradePoint(double score) {
        if (score >= 80) return 4.0;
        if (score >= 75) return 3.7;
        if (score >= 70) return 3.3;
        if (score >= 65) return 3.0;
        if (score >= 60) return 2.7;
        if (score >= 55) return 2.3;
        if (score >= 50) return 2.0;
        if (score >= 40) return 1.7;
        if (score >= 30) return 1.3;
        if (score >= 20) return 1.0;
        return 0.0;
    }
}
