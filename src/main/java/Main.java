import dataLogs.DataLogs;
import userAuthentication.UserAuthentication;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataLogs log = new DataLogs();
        System.out.println("***** Welcome to Database Management System *****");
        System.out.println("MAIN MENU:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Perform DB Operations");
        System.out.println("4. Generate ERD");
        System.out.println("5. General SQL Dump");
        System.out.println("6. Exit");

        String user = sc.next();
        System.out.println("Username: ");
        String username = sc.next();
        System.out.println("Password: ");
        String password = sc.next();

        if (user.equalsIgnoreCase("1")) {
            UserAuthentication.login(username, password);
        } else if (user.equalsIgnoreCase("2")) {
            UserAuthentication.registration(username, password);
            UserAuthentication.login(username, password);
        } else {
            System.out.println("Please select a valid option");
        }
    }
}