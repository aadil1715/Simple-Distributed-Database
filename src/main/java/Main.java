import SQLDump.DataDump;
import dataLogs.DataLogs;
import userAuthentication.UserAuthentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import queryValidator.DBOperations;

public class Main {

    static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        DataLogs log = new DataLogs();
        System.out.println("===============================================");
        System.out.println(" ** Welcome to Database Management System **");
        System.out.println("===============================================");
        System.out.println("MAIN MENU:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Perform DB Operations");
        System.out.println("4. Generate ERD");
        System.out.println("5. General SQL Dump");
        System.out.println("6. Exit");
        int user = sc.nextInt();

        String username = null;
        String password = null;
        if(user == 1 || user == 2) {
            System.out.println("Username: ");
            username = sc.next();
            System.out.println("Password: ");
            password = sc.next();
        }
        if (user == 1) {
            UserAuthentication.login(username, password);
        }
        if (user == 2) {
            UserAuthentication.registration(username, password);
            UserAuthentication.login(username, password);
        }
        if(user == 3){
            DBOperations dbOperations=new DBOperations();
            dbOperations.performDBOperations(username);
        }
        if(user == 5) {
            if(list.size() !=0) {
                DataDump.sqlDump(list);
            }
            else {
                log.logger(Level.WARNING, "No commands have been executed");
            }
        }
        if(user > 6) {
            System.out.println("Please select a valid option");
        }

    }
}