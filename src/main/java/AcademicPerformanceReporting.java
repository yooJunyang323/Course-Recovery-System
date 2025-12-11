/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import java.util.Set;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.mycompany.course_recovery_plan.Failed_student;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.lowagie.text.Font;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;



public class AcademicPerformanceReporting extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AcademicPerformanceReporting.class.getName());

    /**
     * Creates new form AcademicPerformanceReporting
     */
    public AcademicPerformanceReporting() {
        initComponents();
        LoadYear();
    }
    
    public void LoadYear (){
        Student_Information.readCsvData();
        List<Student_Information> data = Student_Information.info;
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        Set<String> yearSet = new HashSet<>();
        
        for (Student_Information info : data){
            String year = info.year;
            yearSet.add(year);
        }
        
        for (String year : yearSet) {
            model.addElement(year);
        }
        System.out.println("Data in student_information.csv:"+data);
        jComboBox1.setModel(model);
    }
    
    public static void generatePdfReport(List<ReportItem> reportList) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Final_Student_Report.pdf"));
            document.open();

            if (reportList.isEmpty()) {
                document.add(new Paragraph("No records found."));
            } else {
                // --- 1. GET STUDENT DETAILS (From the first item in the list) ---
                ReportItem firstItem = reportList.get(0);

                // Create fonts for styling
                Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
                Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

                // Print Header Info (Name, ID, Major)
                document.add(new Paragraph("Student Name: " + firstItem.name, boldFont));
                document.add(new Paragraph("Student ID: " + firstItem.id, boldFont));
                document.add(new Paragraph("Program: " + firstItem.major, boldFont));
                document.add(new Paragraph("Semester: " + firstItem.semester)); // Using semester from your data
                document.add(new Paragraph("\n")); // Add some space

                // --- 2. CREATE THE TABLE ---
                // 5 Columns: Code, Title, Credit, Grade, Grade Point
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100); // Make table span the page width
                // Set relative column widths (e.g., Title is wider than Code)
                table.setWidths(new float[]{2f, 5f, 2f, 2f, 2f}); 

                // --- 3. ADD TABLE HEADERS ---
                addTableHeader(table, "Course Code", boldFont);
                addTableHeader(table, "Course Title", boldFont);
                addTableHeader(table, "Credit Hours", boldFont);
                addTableHeader(table, "Grade", boldFont);
                addTableHeader(table, "Grade Point", boldFont);

                // --- 4. FILL TABLE WITH DATA ---
                for (ReportItem item : reportList) {
                    table.addCell(new Phrase(item.courseCode, normalFont));
                    table.addCell(new Phrase(item.courseName, normalFont));

                    // Credit Hour (You said ignore, but I put the variable here just in case)
                    table.addCell(new Phrase(String.valueOf(item.credit), normalFont));

                    // Grade & Point (Empty for now, as requested "make it look like this")
                    table.addCell(new Phrase("", normalFont)); 
                    table.addCell(new Phrase("", normalFont)); 
                }

                document.add(table);

                // --- 5. ADD FOOTER (CGPA) ---
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Cumulative GPA (CGPA): 3.25", boldFont)); // Hardcoded example
            }

            System.out.println("PDF Created Successfully with Table!");

        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

// Helper method to make the header code cleaner
    private static void addTableHeader(PdfPTable table, String headerTitle, Font font) {
        PdfPCell header = new PdfPCell();
        header.setPhrase(new Phrase(headerTitle, font));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // Optional: Add gray background to header
        // header.setBackgroundColor(java.awt.Color.LIGHT_GRAY); 
        table.addCell(header);
    }
    /*
    public static void generatePdfReport(List<ReportItem> reportList) {
        Document document = new Document();

        try {
            // Output file
            PdfWriter.getInstance(document, new FileOutputStream("Final_Student_Report.pdf"));
            document.open();

            document.add(new Paragraph("Detailed Student Report"));
            document.add(new Paragraph("--------------------------------------------------"));

            if (reportList.isEmpty()) {
                document.add(new Paragraph("No records found for this student."));
            } else {
                // Loop through the NEW ReportItem list
                for (ReportItem item : reportList) {
                    // We access fields from the ReportItem class now
                    String line = "Student: " + item.name + " (" + item.id + ")\n" +
                                  "Major: " + item.major + "\n" +
                                  "Course: " + item.courseName + " (" + item.courseCode + ")\n" +
                                  "Semester: " + item.semester + " | Credits: " + item.credit + 
                                  " | Attempt: " + item.attemptNum + "\n" +
                                  "--------------------------------------------------";

                    document.add(new Paragraph(line));
                }
            }

            System.out.println("PDF Created Successfully!");

        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
} */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Academic Performance Report");

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
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Export to PDF");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Year : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // student_information.csv > name, major
        // student_assessment.csv > taken course
        // course_informtation.csv > semester, course info
        // id in student_information same with student_assessment and find the course study, then display course information
        
        // student_information.csv
        Student_Information.readCsvData();
        List<Student_Information> data = Student_Information.info; 
        // student_assessment.csv
        Failed_student.readCsvData();
        List <Failed_student> student = Failed_student.students;
        // course_informtation.csv
        Course_Information.readCsvData();
        List<Course_Information> course = Course_Information.courseInfo;
        List<ReportItem> finalReportList = new ArrayList<>(); // avoid add same course with same student
        
        
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            System.out.println("Please select a row first.");
            return;
        }
        
        String id = jTable1.getValueAt(row, 0).toString();
        String name = jTable1.getValueAt(row, 1).toString();
        String major = jTable1.getValueAt(row, 2).toString();
        

        
        /*
        for (Failed_student student1: student){
            if (id.equalsIgnoreCase(student1.studentID.trim())){
                String courseCode = student1.courseCode;
                int attemptNum = student1.attemptNo;
                for (Course_Information sc: c){
                    if(courseCode.equalsIgnoreCase(sc.courseCode.trim())){
                        String courseName = sc.tittle;
                        int semester = sc.semester;
                        int credit = sc.credit;
                        
                        //stud_Course.add
                    }
                }
                
            }
        } */
        
        for (Failed_student fs : student) {
        // Check if this failed record belongs to the selected student
            if (id.equalsIgnoreCase(fs.studentID.trim())) {

                String courseCode = fs.courseCode;
                int attemptNum = fs.attemptNo;

                // Find course details
                for (Course_Information c : course) {
                    if (courseCode.equalsIgnoreCase(c.courseCode.trim())) {

                        String courseName = c.tittle;
                        int semester = c.semester;
                        int credit = c.credit;

                        // --- COMBINE DATA INTO OBJECT AND ADD TO LIST ---
                        ReportItem item = new ReportItem(
                            id, name, major, 
                            courseCode, courseName, 
                            semester, credit, attemptNum
                        );

                        finalReportList.add(item);
                    }
                }
            }
        }
        
        
        System.out.println("Items found: " + finalReportList.size());
        generatePdfReport(finalReportList);
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String year = jComboBox1.getSelectedItem().toString();
        System.out.println("Year selected: "+year);
        //String [] students_list;
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Name","Major"}, 0
        );
        
        List<Student_Information> data = Student_Information.info;
        for (Student_Information studInfo : data ){
            String stu_year = studInfo.year;
            if (stu_year.equalsIgnoreCase(year.trim())){
                model.addRow(new Object[]{
                    studInfo.id,
                    studInfo.name,
                    studInfo.major,
                });
            }
        }
        
        jTable1.setModel(model);
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
        java.awt.EventQueue.invokeLater(() -> new AcademicPerformanceReporting().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
