/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

//add new import
import java.security.SecureRandom;
/*
 *
 * @author gk005
 */
public class gmailservice {
    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    private static final String SENDER_EMAIL = "kobayakawa401@gmail.com"; // YOUR GMAIL
    private static final String APP_PASSWORD = "zxgp exci jnqg uqpg"; // YOUR APP PASSWORD
    
    private int currentOtp;
        
        //gene otp
        public int generateOTP(){
            SecureRandom random = new SecureRandom();
            currentOtp = 100000 + random.nextInt(900000);
            return currentOtp;
        }
        
        //gene send mail
        public boolean sendOTPEmail(String UserEmail){
            int otp = generateOTP();
            
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
                }
            });
            
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(SENDER_EMAIL));
                message.setSubject("Your OTP Code");
                
                String body = "Hello " + UserEmail + ",\nYour OTP is: " + otp;
                message.setText(body);

                Transport.send(message);
                
                return true;

            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        }


        public int getCurrentOtp() {
            return currentOtp;
        }
        

    
    /*
    public class EmailService {

        private static final String SENDER_EMAIL = "kobayakawa401@gmail.com"; // YOUR GMAIL
        private static final String APP_PASSWORD = "ydgp ntpg vxkr yidv"; // YOUR APP PASSWORD

        /*
        public static void sendRecoveryPlan(String recipientEmail, List<Recovery_plan> planData) {

            if (planData == null || planData.isEmpty()) return;

            Recovery_plan firstRecord = planData.get(0);
            String studentID = firstRecord.studentID;
            String courseCode = firstRecord.courseCode;
            //String courseName = getCourseName(courseCode); // Helper to get full name
            String recommendation = firstRecord.recommendation;

            // 1. Setup Gmail Properties
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");

            // 2. Create Session
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
                }
            });

            try {
                // 3. Create the Message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL));
                message.setRecipients(
                    Message.RecipientType.TO, 
                    InternetAddress.parse(recipientEmail)
                );
                message.setSubject("Your Course Recovery Plan - "+ courseCode);

                // 4. Build the Email Content (HTML Table)
                String htmlContent = buildHtmlTable(studentID, courseCode, recommendation,planData);

                // Set content as HTML so the table renders correctly
                message.setContent(htmlContent, "text/html; charset=utf-8");

                // 5. Send
                Transport.send(message);
                System.out.println("Email sent successfully to " + recipientEmail);
                javax.swing.JOptionPane.showMessageDialog(null, "Plan sent to " + recipientEmail);

            } catch (MessagingException e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Error sending email: " + e.getMessage());
            }
        }

        // Helper method to format the list into an HTML table
        private static String buildHtmlTable(String studentID, String code, String rec,List<Recovery_plan> plan) {
            String name = "courseName";
            StringBuilder sb = new StringBuilder();
            String containerStyle = "font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;";
            String headerStyle = "color: #333; font-size: 24px; margin-bottom: 10px; border-bottom: 2px solid #0056b3; padding-bottom: 10px;";
            String recBoxStyle = "background-color: #f0f7ff; border-left: 5px solid #0056b3; padding: 15px; margin: 20px 0; color: #333;";
            String tableStyle = "width: 100%; border-collapse: collapse; margin-top: 20px;";
            String thStyle = "background-color: #f8f9fa; border-bottom: 2px solid #dee2e6; padding: 12px; text-align: left; color: #495057;";
            String tdStyle = "padding: 12px; border-bottom: 1px solid #dee2e6; vertical-align: middle;";

            // --- HTML BODY ---
            sb.append("<html><body style='margin:0; padding:20px; background-color: #f4f4f4;'>");

            // Main Container Card
            sb.append("<div style='" + containerStyle + " background-color: white;'>");

            // Header Title
            sb.append("<h1 style='" + headerStyle + "'>Course Recovery Plan</h1>");

            // Greeting & Intro
            sb.append("<p>Dear <b>").append(studentID).append("</b>,</p>");
            sb.append("<p>We have created a personalized course recovery plan to help you succeed in <b>")
              .append(name).append(" (").append(code).append(")</b>. ");
            sb.append("Please review the recommendations and complete the action plan below.</p>");


            if (rec != null && !rec.isEmpty()) {
                sb.append("<div style='" + recBoxStyle + "'>");
                sb.append("<strong style='color: #0056b3;'>Recovery Recommendation:</strong><br/>");
                sb.append("<span style='font-size: 14px;'>").append(rec).append("</span>");
                sb.append("</div>");
            }

            sb.append("<h3>Action Plan Timeline:</h3>");


            sb.append("<table style='" + tableStyle + "'>");
            sb.append("<tr>")
              .append("<th style='" + thStyle + "'>Week</th>")
              .append("<th style='" + thStyle + "'>Task</th>")
              .append("<th style='" + thStyle + "'>Status</th>")
              .append("</tr>");

            for (Recovery_plan row : plan) {

                String badgeStyle;
                String statusText;

                if (row.status) { 
                    badgeStyle = "background-color: #d4edda; color: #155724; padding: 5px 10px; border-radius: 15px; font-size: 12px; font-weight: bold; display: inline-block;";
                    statusText = "Completed";
                } else { 
                    badgeStyle = "background-color: #fff3cd; color: #856404; padding: 5px 10px; border-radius: 15px; font-size: 12px; font-weight: bold; display: inline-block;";
                    statusText = "Pending";
                }

                sb.append("<tr>");
                sb.append("<td style='" + tdStyle + "'>").append(row.weekNo).append("</td>");
                sb.append("<td style='" + tdStyle + "'>").append(row.task).append("</td>");
                sb.append("<td style='" + tdStyle + " text-align: center;'>");
                sb.append("<span style='" + badgeStyle + "'>").append(statusText).append("</span>");
                sb.append("</td></tr>");
            }
            sb.append("</table>");


            sb.append("<br/><hr style='border: 0; border-top: 1px solid #eee;'/>");
            sb.append("<p style='color: #777; font-size: 12px;'>");
            sb.append("<b>Next Steps:</b> Please start working on the action plan immediately.<br/>");
            sb.append("Best regards,<br/>Academic Recovery Team");
            sb.append("</p>");

            sb.append("</div>"); 
            sb.append("</body></html>");

            return sb.toString();
        }


        private static String getCourseName(String code) {
            if (code == null) return "Unknown Course";
            code = code.trim().toUpperCase();


            switch (code) {
                case "CS201": return "Data Structures";
                case "C219": return "Programming Concepts";
                case "C204": return "Database Systems";
                case "C216": return "Software Engineering";
                default: return "Course"; 
            }
        }
        */
   
}
