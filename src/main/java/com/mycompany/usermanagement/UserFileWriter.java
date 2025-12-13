/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author gk005
 */
public class UserFileWriter {
    private String filePath;

    public UserFileWriter(String filePath) {
        this.filePath = filePath;
    }
    
    public boolean updateUserByID(String targetID, User updatedUser) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePath));
            int targetIndex = -1;

            for (int i = 0; i < allLines.size(); i++) {
                String[] values = allLines.get(i).split(",");
                if (values.length > 0 && values[0].equals(targetID)) {
                    targetIndex = i;
                    break;
                }
            }

            if (targetIndex == -1) {
                System.err.println("User ID not found: " + targetID);
                return false;
            }

            allLines.set(targetIndex, updatedUser.toCSVString());

            Files.write(Paths.get(filePath), allLines,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);

            return true;

        } catch (IOException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    
    public boolean appendUser(User user){
        try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.APPEND, StandardOpenOption.CREATE)){
            bw.write(user.toCSVString());
            bw.newLine();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
