import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class StudentDetailsFrame extends JFrame {

    private final String studentId;
    private final String studentFile;
    private final String courseFile;
    private final String studentAssessmentFile;
    private final StudentDataManager parentRef;

    private JLabel lblTitle, lblInfo, lblFailedCount;
    private JTable failedTable;

    public StudentDetailsFrame(String studentId, String studentFile, String courseFile, String studentAssessmentFile, StudentDataManager parentRef) {
        this.studentId = studentId;
        this.studentFile = studentFile;
        this.courseFile = courseFile;
        this.studentAssessmentFile = studentAssessmentFile;
        this.parentRef = parentRef;

        initUI();
        loadStudentData();
    }

    private void initUI() {
        setTitle("Student Details");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // === Colors ===
        Color bgMain = new Color(17, 24, 39);
        Color bgBox = new Color(31, 41, 55);
        Color accent = new Color(59, 130, 246);
        Color textColor = Color.WHITE;

        // === Panels ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgMain);
        add(mainPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(bgMain);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        lblTitle = new JLabel("Student Details");
        lblTitle.setForeground(accent);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        lblInfo = new JLabel();
        lblInfo.setForeground(textColor);
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        topPanel.add(lblInfo, BorderLayout.CENTER);

        lblFailedCount = new JLabel();
        lblFailedCount.setForeground(Color.LIGHT_GRAY);
        lblFailedCount.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblFailedCount.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        topPanel.add(lblFailedCount, BorderLayout.SOUTH);
                
        // === Table Section ===
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(bgBox);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] columns = {"Course ID", "Attempt Number", "Exam", "Assignment", "GPA", "Failed Components"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        failedTable = new JTable(model);
        failedTable.setBackground(bgBox);
        failedTable.setForeground(textColor);
        failedTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        failedTable.getTableHeader().setBackground(bgMain);
        failedTable.getTableHeader().setForeground(accent);
        failedTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        failedTable.getTableHeader().setReorderingAllowed(false);
        failedTable.getTableHeader().setResizingAllowed(false);
        failedTable.setGridColor(new Color(59, 59, 59));
        failedTable.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(failedTable);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bgBox);
        tablePanel.add(scroll, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private void loadStudentData() {
        try {
            // Get basic info
            String name = "", major = "", year = "", email = "";
            try (BufferedReader br = new BufferedReader(new FileReader(studentFile))) {
                String line;
                boolean first = true;
                while ((line = br.readLine()) != null) {
                    if (first) { first = false; continue; }
                    String[] data = line.split(",");
                    if (data[0].trim().equalsIgnoreCase(studentId)) {
                        name = data[1].trim() + " " + data[2].trim();
                        major = data[3].trim();
                        year = data[4].trim();
                        email = data[5].trim();
                        break;
                    }
                }
            }

            // Get grades
            List<String[]> grades = parentRef.getStudentGrades(studentId);

            int failedCount = 0;
            double totalGPA = 0.0;
            int totalCredits = 0;

            DefaultTableModel model = (DefaultTableModel) failedTable.getModel();
            model.setRowCount(0);

            for (String[] course : grades) {
                String courseId = course[0];
                double gpa = Double.parseDouble(course[1]);
                double exam = Double.parseDouble(course[2]);
                double assign = Double.parseDouble(course[3]);
                int attempt = Integer.parseInt(course[4]);
                int credit = parentRef.getCredit(courseId);

                totalGPA += gpa * credit;
                totalCredits += credit;

                String failedComp = "";
                if (exam < 50) failedComp += "Exam ";
                if (assign < 50) failedComp += "Assignment";
                if (failedComp.equals("Exam Assignment")) failedComp = "Exam & Assignment";

                if (gpa < 2.0 || !failedComp.isEmpty()) {
                    failedCount++;
                    model.addRow(new Object[]{
                        courseId,
                        attempt,
                        String.format("%.0f", exam),
                        String.format("%.0f", assign),
                        String.format("%.2f", gpa),
                        failedComp.isEmpty() ? "None" : failedComp
                    });
                }
            }

            double cgpa = totalCredits == 0 ? 0 : totalGPA / totalCredits;
            lblInfo.setText(String.format(
                "<html><body>Student ID: %s<br>Name: %s<br>Major: %s<br>Year: %s<br>Email: %s<br>CGPA: <b>%.2f</b></body></html>",
                studentId, name, major, year, email, cgpa
            ));

            lblFailedCount.setText("Number of failed courses: " + failedCount);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading details: " + e.getMessage());
        }
    }
}
