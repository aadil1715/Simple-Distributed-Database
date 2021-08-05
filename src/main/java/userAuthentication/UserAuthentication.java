package userAuthentication;

import dataLogs.DataLogs;

import java.io.*;
import java.util.logging.Level;

public class UserAuthentication {
    static DataLogs log = new DataLogs();

    public static void registration(String username, String password) {
        try {
            String encryptedPassword = Encryption.encrypt(password);
            BufferedWriter writer = new BufferedWriter(new FileWriter("userLoginDetails.txt"));
            writer.append(username).append("??").append(encryptedPassword).append("\n");
            writer.close();
            log.logger(Level.INFO, "User is successfully registered");
        } catch (IOException e) {
            e.printStackTrace();
            log.logger(Level.SEVERE, "User registration failed");
        }
    }

    public static void login(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userLoginDetails.txt"));
            String line;
            boolean userMatches = false;
            while ((line = reader.readLine()) != null) {
                String[] attribute = line.split("\\?\\?");
                String retrieveUsername = attribute[0];
                if (username.equals(retrieveUsername)) {
                    String decryptedPassword = Encryption.decrypt(attribute[1]);
                    if (decryptedPassword.equals(password)) {
                        System.out.println("Login with these credentials is successful");
                        log.logger(Level.INFO, "Login successful");
                        userMatches = true;
                    } else {
                        System.out.println("Login with these credentials has failed");
                        log.logger(Level.WARNING, "Login failed");
                        userMatches = true;
                    }
                    break;
                }
            }
            if (!userMatches) {
                System.out.println("This user is not registered. Please proceed with registration.");
                log.logger(Level.WARNING, "User not registered with the system");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.logger(Level.SEVERE, "Login failed");
        }
    }
}