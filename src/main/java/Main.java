import ERDGenerator.ERD;
import SQLDump.DataDump;
import dataLogs.DataLogs;
import queryValidator.CreateValidator;
import userAuthentication.UserAuthentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import queryValidator.DBOperations;

public class Main {

    static List<String> list = new ArrayList<>();
    public static  String username = "";
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("=============================================");
        System.out.println(" ** Welcome to Database Management System ** ");
        System.out.println("=============================================");
        System.out.println("MAIN MENU:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        int user = sc.nextInt();

        String password = "";
        if (user == 1 || user == 2) {
            System.out.println("Username: ");
            username = sc.next();
            System.out.println("Password: ");
            password = sc.next();

            if(user == 2) {
                UserAuthentication.registration(username, password);
            }
            UserAuthentication.login(username, password);
            performOperations(username);
        }
        else if (user > 3) {
            System.out.println("Select correct option");
        }
    }

    private static void performOperations(String username) throws IOException {
        DataLogs log = new DataLogs();
        System.out.println("Please select the following operation");
        System.out.println("1. Perform DB Operations");
        System.out.println("2. Perform Transactions");
        System.out.println("3. Generate ERD");
        System.out.println("4. Exit");
        Scanner scanner = new Scanner(System.in);
        int operation=scanner.nextInt();

        if(operation == 1) {
            DBOperations dbOperations=new DBOperations();
            try {
                dbOperations.performDBOperations(username,list);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                log.logger(Level.INFO,"Generating SQL Dump");
                DataDump.sqlDump(list);
            } else {
                log.logger(Level.WARNING, "No commands have been executed");
            }
        }
        else if (operation==2) {
            ArrayList<String> transaction=new ArrayList<>();
            System.out.println("BEGIN TRANSACTION:");
            while(true) {
                String input = scanner.nextLine();
                if(input.equals("commit"))
                    break;
                transaction.add(input);
            }
            for (int i=0;i<transaction.size();i++){
                DBOperations dbOperations=new DBOperations();
                dbOperations.executeQueries(transaction.get(i),username,list);
            }
        }
        else if (operation == 3) {
            ERD.generateERD();
        }
    }
}