/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.course_recovery_plan;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import javax.swing.*;
import java.awt.*;

public class PdfViewerForm extends JFrame {

    public PdfViewerForm() {
        // 1. Setup the Frame
        setTitle("Student Report Viewer");
        setSize(800, 600); // Set a good size to view documents
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window, not the whole app
        setLocationRelativeTo(null); // Center the window

        // 2. Initialize ICEpdf Controller
        SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);

        // 3. Build the Viewer Panel
        // This creates the toolbar, scrollbars, and document view automatically
        JPanel viewerComponentPanel = factory.buildViewerPanel();

        // 4. Add the Viewer to your Frame
        getContentPane().add(viewerComponentPanel, BorderLayout.CENTER);

        // 5. Open the specific PDF file
        // Make sure this matches the filename you used in your generate function!
        String filePath = "Final_Student_Report.pdf"; 
        
        try {
            controller.openDocument(filePath);
            System.out.println("Opening PDF: " + filePath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not open PDF: " + e.getMessage());
        }
    }

    // Main method for testing this form individually
    public static void main(String[] args) {
        // Ensure GUI runs on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new PdfViewerForm().setVisible(true);
        });
    }
}
