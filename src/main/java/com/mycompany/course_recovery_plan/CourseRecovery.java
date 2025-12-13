/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.course_recovery_plan;

import java.util.List;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;




public class CourseRecovery extends javax.swing.JFrame {
    private List<Failed_student> filteredStudents;
    private List<Recovery_plan> specificPlan;
    private List<Failed_student> selectedStudentsList;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CourseRecovery.class.getName());
    private String selectedRecommendation;  

    
    
    public CourseRecovery() {
        initComponents();
        jPanel11.setVisible(false);
        LoadStudents();
        
    }
    
    public void refreshStudentList() {
        LoadStudents(); // your existing method to reload table or JList
    }

    public final void LoadStudents(){
        Failed_student.readCsvData();
        List<Failed_student> studentsData = Failed_student.students;
        DefaultListModel<String> listModel = new DefaultListModel<>();
        filteredStudents = new java.util.ArrayList<>();
        final int MIN_EXAM_PASS = 40;
        final int MIN_ASSIGNMENT_PASS = 50;
        for (Failed_student student : studentsData) {
            int numAttempt = student.attemptNo;
            boolean failedExam = student.examGrade<MIN_EXAM_PASS;
            boolean failedAssignment = student.assignmentGrade<MIN_ASSIGNMENT_PASS;
            
            
            if (failedExam || failedAssignment){
                String summaryLine = String.format(
                "ID: %s | Course: %s | Attempt No: %s | Exam Grade: %d | Assignment Grade: %d | Recovery Plan: %s",
                student.studentID,
                student.courseCode,
                student.attemptNo,
                student.examGrade,
                student.assignmentGrade,
                student.recoveryPlan ? "TRUE" : "FALSE" );
        
                listModel.addElement(summaryLine);
                filteredStudents.add(student);
            }
            
        }

        jList1.setModel(listModel);
        
    }
    
    public final void Load_plan(String studentID,String courseCode){
        jPanel11.setVisible(true);
        System.out.println("This is load plan");
        System.out.println("Load_plan Student ID: " + studentID);
        System.out.println("Load_plan courseCode: " + courseCode);

        Recovery_plan.readCsvData();
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Week","Task","Status"}, 0
        ){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class; 
                }
                return String.class; 
            }
            @Override
            public boolean isCellEditable(int row, int column) {

                if (column == 2) return true; 
                return true; 
            }
        };
        
        
        List<Recovery_plan> planData = Recovery_plan.plan;
        System.out.println("Plan data = " + planData);
        specificPlan = new java.util.ArrayList<>();
        boolean found = false;
        
        
        
        for(Recovery_plan s : planData){
            System.out.println("Student id and courseCode in for loop: "+s.studentID +","+s.courseCode);
            if (s.studentID.trim().equalsIgnoreCase(studentID.trim()) && s.courseCode.equalsIgnoreCase(courseCode)){
                specificPlan.add(s);
                System.out.println("match id and coursecode");
                model.addRow(new Object[]{
                    s.weekNo,
                    s.task,
                    s.status,
                });
                
                found = true;
            } else {
                System.out.println("no match id and course code in Load plan: "+ studentID+","+courseCode);
            }
            
            
                    
        }
        
        jTable1.setModel(model);
        
        
        model.addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (row < 0 || row >= specificPlan.size()) return;

                    Object newValue = model.getValueAt(row, column);
                    Recovery_plan selectedPlan = specificPlan.get(row);

                    System.out.println("Updating Column " + column + " to: " + newValue);

                    switch (column) {
                        case 0: 
                            selectedPlan.weekNo = Integer.parseInt(newValue.toString());
                            break;
                        case 1: 
                            selectedPlan.task = newValue.toString();
                            break;
                        case 2: 
                            selectedPlan.status = (Boolean) newValue;
                            break;

                        default:
                            javax.swing.JOptionPane.showMessageDialog(null, "ID and Course cannot be edited.");
                            return; 
                    }

                    Recovery_plan.saveCsvData(); 
                    System.out.println("Auto-saved change to CSV.");
                }
            }
        });
        
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {               
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE || 
                    evt.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE) {

                    int selectedRow = jTable1.getSelectedRow();


                    if (selectedRow >= 0) {

                        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                            null, 
                            "Are you sure you want to delete this task?", 
                            "Delete Confirmation", 
                            javax.swing.JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                            Recovery_plan planToRemove = specificPlan.get(selectedRow);
                            specificPlan.remove(selectedRow);
                            Recovery_plan.plan.remove(planToRemove);
                            Recovery_plan.saveCsvData();
                            model.removeRow(selectedRow);
                            System.out.println("Row deleted and CSV updated.");
                        }
                    }
                }
            }
        });
        

        if (!specificPlan.isEmpty()){
            selectedRecommendation = specificPlan.get(0).recommendation;
            System.out.println("Stored recommendation is not empty: " + selectedRecommendation);
            jTextField1.setText(String.valueOf(selectedRecommendation));
        } else {
            selectedRecommendation = "";
            System.out.println("Stored recommendation is empty: " + selectedRecommendation);
        }
        
        
    }
    
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel3.setBorder(
            new javax.swing.border.LineBorder(
                new java.awt.Color(0, 0, 0, 120), // RGBA (A=opacity)
                2, // thickness
                true // rounded corners
            )
        );
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setOpaque(false);
        jPanel4.setBorder(
            new javax.swing.border.MatteBorder(
                0,0,1,1,
                new java.awt.Color(0,0,0,120)// RGBA (A=opacity)

            )
        );
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        pnlFailTemplate = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        pnlFailTemplate1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(17, 24, 39));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(31, 41, 55));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(59, 130, 246));
        jLabel1.setText("Course Recovery Plan");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(229, 231, 235));
        jLabel5.setText("Manage and monitor course recovery activities  ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 660, 50));

        jPanel3.setBackground(java.awt.Color.black);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("User");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Academic officer");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 140, 50));

        jPanel4.setBackground(java.awt.Color.black);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(59, 130, 246));
        jLabel2.setText("CRS");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(229, 231, 235));
        jLabel3.setText("Course Recovery System");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 50));

        jPanel6.setBackground(java.awt.Color.black);

        jPanel5.setBackground(new java.awt.Color(51, 204, 255));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("User management");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(51, 204, 255));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Academic Reports");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(51, 204, 255));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Eligibility & Enrolment ");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(51, 204, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(java.awt.Color.white, 1, true));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Course Recovery Plan");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(476, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 140, 620));

        jPanel10.setBackground(new java.awt.Color(31, 41, 55));
        jPanel10.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(java.awt.Color.white, 1, true), new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setText("Failed students:");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setOpaque(false);

        jPanel13.setBackground(java.awt.Color.black);
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setForeground(java.awt.Color.white);
        jLabel13.setText("Student :");
        jPanel13.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jLabel14.setForeground(java.awt.Color.white);
        jLabel14.setText("( id )");
        jPanel13.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, -5, 43, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel15.setForeground(java.awt.Color.white);
        jLabel15.setText("Bachelor of ");
        jPanel13.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, 134, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(java.awt.Color.red);
        jLabel16.setText("num fail");
        jPanel13.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        jPanel11.setBackground(new java.awt.Color(31, 41, 55));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(java.awt.Color.white);
        jLabel17.setText("Course Recovery Plan");

        jPanel14.setBackground(java.awt.Color.black);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(java.awt.Color.white);
        jLabel30.setText("Course Name");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel31.setForeground(java.awt.Color.white);
        jLabel31.setText("Course code : ");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel32.setForeground(java.awt.Color.white);
        jLabel32.setText("jLabel32");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 600, 200));

        jLabel18.setForeground(java.awt.Color.white);
        jLabel18.setText("Name");
        jPanel13.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 6, -1, -1));

        jPanel12.setBackground(new java.awt.Color(31, 41, 55));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(java.awt.Color.white);
        jLabel20.setText("Course ID");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(java.awt.Color.white);
        jLabel19.setText("- ");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(java.awt.Color.white);
        jLabel21.setText("Course Name");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel22.setForeground(java.awt.Color.white);
        jLabel22.setText("Credit hour : ");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel26.setForeground(java.awt.Color.white);
        jLabel26.setText("hour");

        pnlFailTemplate.setBackground(new java.awt.Color(17, 24, 39));

        jLabel23.setForeground(java.awt.Color.red);
        jLabel23.setText("jLabel23");

        jLabel24.setForeground(java.awt.Color.white);
        jLabel24.setText("Failed component");

        javax.swing.GroupLayout pnlFailTemplateLayout = new javax.swing.GroupLayout(pnlFailTemplate);
        pnlFailTemplate.setLayout(pnlFailTemplateLayout);
        pnlFailTemplateLayout.setHorizontalGroup(
            pnlFailTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFailTemplateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addContainerGap())
        );
        pnlFailTemplateLayout.setVerticalGroup(
            pnlFailTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFailTemplateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFailTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlFailTemplate1.setBackground(new java.awt.Color(17, 24, 39));

        jLabel27.setForeground(java.awt.Color.red);
        jLabel27.setText("jLabel27");

        jLabel28.setForeground(java.awt.Color.white);
        jLabel28.setText("Failed component");

        javax.swing.GroupLayout pnlFailTemplate1Layout = new javax.swing.GroupLayout(pnlFailTemplate1);
        pnlFailTemplate1.setLayout(pnlFailTemplate1Layout);
        pnlFailTemplate1Layout.setHorizontalGroup(
            pnlFailTemplate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFailTemplate1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addContainerGap())
        );
        pnlFailTemplate1Layout.setVerticalGroup(
            pnlFailTemplate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFailTemplate1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFailTemplate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton3.setBackground(new java.awt.Color(59, 130, 246));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Send to gmail");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlFailTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26))
                            .addComponent(pnlFailTemplate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFailTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFailTemplate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 600, 150));

        jPanel17.setBackground(new java.awt.Color(31, 41, 55));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Recommendation");

        jTextField1.setBackground(new java.awt.Color(17, 24, 39));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));

        jButton2.setText("Update");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jLabel25.setForeground(new java.awt.Color(255, 51, 102));
        jLabel25.setText("Delete");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addContainerGap())
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 360, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel13.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 600, 90));

        jScrollPane4.setViewportView(jPanel13);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 660, 630));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(59, 130, 246));
        jLabel8.setText("Course Recovery Plan");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 160, 30));

        jButton1.setBackground(new java.awt.Color(59, 130, 246));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(java.awt.Color.white);
        jButton1.setText("Create Recovery Plan");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, -1, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        jTextField1.setText("");
        if (!evt.getValueIsAdjusting() && jList1.getSelectedIndex() != -1) {
            
            int selectedIndex = jList1.getSelectedIndex();
            

            if (selectedIndex >= 0 && selectedIndex < filteredStudents.size()) {

                Failed_student selectedStudent = filteredStudents.get(selectedIndex);
                jLabel14.setText(String.format("( %s )", selectedStudent.studentID));
                jLabel20.setText(String.format("(%s)",selectedStudent.courseCode));
                selectedStudentsList = new ArrayList<>();

                System.out.println("Selected Student ID: " + selectedStudent.studentID);
                System.out.println("Selected Student courseCode: " + selectedStudent.courseCode);
                System.out.println("Selected Student assignmentGrade: " + selectedStudent.assignmentGrade);
                System.out.println("Selected Student examGrade: " + selectedStudent.examGrade);

                String failedExam = "Exam ";
                String failedAssignment = "Assignemnt";
                
                if (selectedStudent.examGrade < 40 && selectedStudent.assignmentGrade < 50 && selectedStudent.attemptNo < 3){   
                    jLabel24.setText(failedExam);
                    jLabel23.setText(String.format("%s/100",selectedStudent.examGrade));
                    jLabel28.setText(failedAssignment);
                    jLabel27.setText(String.format("%s/100",selectedStudent.assignmentGrade));
                    if (selectedStudent.attemptNo == 1) {
                        jLabel16.setText(String.format("%sst Attempt",selectedStudent.attemptNo));
                        jLabel16.setForeground(Color.YELLOW);
                    } else if (selectedStudent.attemptNo == 2) {
                        jLabel16.setText(String.format("%snd Attempt",selectedStudent.attemptNo));
                        jLabel16.setForeground(Color.ORANGE);
                    }
                    pnlFailTemplate1.setVisible(true);
                    jPanel11.setVisible(true);
                    selectedStudentsList.add(selectedStudent);
                    System.out.println("failed exam and assignment");
                    Load_plan(selectedStudent.studentID,selectedStudent.courseCode);
                    
                } else if ((selectedStudent.examGrade < 40 || selectedStudent.assignmentGrade < 50) && selectedStudent.attemptNo > 2){
                    jLabel24.setText(failedExam);
                    jLabel23.setText(String.format("%s/100",selectedStudent.examGrade));
                    jLabel28.setText(failedAssignment);
                    jLabel27.setText(String.format("%s/100",selectedStudent.assignmentGrade));
                    jLabel16.setText(String.format("%srd Attempt (All Components Required)",selectedStudent.attemptNo));
                    jLabel16.setForeground(Color.RED);
                    selectedStudentsList.add(selectedStudent);
                    Load_plan(selectedStudent.studentID,selectedStudent.courseCode);
                    
                } else if (selectedStudent.examGrade < 40 && selectedStudent.attemptNo < 3){
                    jLabel24.setText(failedExam);
                    jLabel23.setText(String.format("%s/100",selectedStudent.examGrade));
                    if (selectedStudent.attemptNo == 1) {
                        jLabel16.setText(String.format("%sst Attempt",selectedStudent.attemptNo));
                        jLabel16.setForeground(Color.YELLOW);
                    } else if (selectedStudent.attemptNo == 2) {
                        jLabel16.setText(String.format("%snd Attempt",selectedStudent.attemptNo));
                        jLabel16.setForeground(Color.ORANGE);
                    }
                    System.out.println("failed exam ");
                    System.out.println("Failed exam Student ID: " + selectedStudent.studentID);
                    System.out.println("Failed exam courseCode: " + selectedStudent.courseCode);
                    pnlFailTemplate1.setVisible(false);
                    selectedStudentsList.add(selectedStudent);
                    Load_plan(selectedStudent.studentID,selectedStudent.courseCode);
                    
                } else if (selectedStudent.assignmentGrade < 50 && selectedStudent.attemptNo < 3){
                    jLabel24.setText(failedAssignment);
                    jLabel23.setText(String.format("%s/100",selectedStudent.assignmentGrade));
                    if (selectedStudent.attemptNo == 1) {
                        jLabel16.setText(String.format("%sst Attempt",selectedStudent.attemptNo));
                        jLabel16.setForeground(Color.YELLOW);
                    } else if (selectedStudent.attemptNo == 2) {
                        jLabel16.setText(String.format("%snd Attempt",selectedStudent.attemptNo));
                        jLabel16.setForeground(Color.ORANGE);
                    }
                    pnlFailTemplate1.setVisible(false);
                    System.out.println("failed assignment");
                    selectedStudentsList.add(selectedStudent);
                    Load_plan(selectedStudent.studentID,selectedStudent.courseCode);
                    
                } 
            }
        }        
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        createPlan create = new createPlan(selectedStudentsList,this);
        
        if (selectedRecommendation!=null){
            create.setRecommendation(selectedRecommendation);
        } else {
            create.setRecommendation(selectedRecommendation);
        }
        
        create.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

        String newRecommendation = jTextField1.getText();
        String rawID = jLabel14.getText();
        String rawCourse = jLabel20.getText();
        String studentID = rawID.replace("(", "").replace(")", "").trim();
        String courseCode = rawCourse.replace("(", "").replace(")", "").trim();
        boolean found = false;
        
        Recovery_plan.readCsvData();
        List<Recovery_plan> planData = Recovery_plan.plan;
        for(Recovery_plan s : planData){
            System.out.println("Student id and courseCode in update button: "+s.studentID +","+s.courseCode);
            if (s.studentID.trim().equalsIgnoreCase(studentID.trim()) && s.courseCode.equalsIgnoreCase(courseCode)){                specificPlan.add(s);
                System.out.println("match id and coursecode in update button");
                found = true;
                s.recommendation = newRecommendation;
            } else {
                System.out.println("no match id and course code in update button: "+ studentID+","+courseCode);
            }
            
        }
        
        if (found){
            Recovery_plan.saveCsvData();
            javax.swing.JOptionPane.showMessageDialog(this, "Recommendation Updated!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Student/Course record not found.");
        }
            
        
        
    }//GEN-LAST:event_jButton2MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        jTextField1.setText("");
        String rawID = jLabel14.getText();
        String rawCourse = jLabel20.getText();
        String studentID = rawID.replace("(", "").replace(")", "").trim();
        String courseCode = rawCourse.replace("(", "").replace(")", "").trim();
        boolean found = false;
        
        Recovery_plan.readCsvData();
        List<Recovery_plan> planData = Recovery_plan.plan;
        for(Recovery_plan s : planData){
            System.out.println("Student id and courseCode in update button: "+s.studentID +","+s.courseCode);
            if (s.studentID.trim().equalsIgnoreCase(studentID.trim()) && s.courseCode.equalsIgnoreCase(courseCode)){                specificPlan.add(s);
                System.out.println("match id and coursecode in update button");
                found = true;
                s.recommendation = "";
            } else {
                System.out.println("no match id and course code in update button: "+ studentID+","+courseCode);
            }
            
            
        }
        if (found){
            Recovery_plan.saveCsvData();
            javax.swing.JOptionPane.showMessageDialog(this, "Recommendation Deleted!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Student/Course record not found.");
        }
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        if (specificPlan == null || specificPlan.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "No plan loaded to send!");
            return;
        }

        String studentEmail = javax.swing.JOptionPane.showInputDialog(this, "Enter Student Email:");

        if (studentEmail != null && !studentEmail.trim().isEmpty()) {

            EmailService.sendRecoveryPlan(studentEmail, specificPlan);
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        IneligibleStudentsTable popUpWindow = new IneligibleStudentsTable();

        popUpWindow.setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        //
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        AcademicPerformanceReporting popUpWindow = new AcademicPerformanceReporting();
        popUpWindow.setVisible(true);
    }//GEN-LAST:event_jLabel9MouseClicked


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new CourseRecovery().setVisible(true));
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel pnlFailTemplate;
    private javax.swing.JPanel pnlFailTemplate1;
    // End of variables declaration//GEN-END:variables
}
