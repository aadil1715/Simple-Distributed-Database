import dataLogs.DataLogs;
import userAuthentication.UserAuthentication;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Console...");
        Scanner sc = new Scanner(System.in);

        DataLogs log = new DataLogs();
        System.out.println("Welcome....");
        System.out.println("Existing user? please enter \"login\" or New user? please enter \"register\": ");
        String user = sc.next();
        System.out.println("Username: ");
        String username = sc.next();
        System.out.println("Password: ");
        String password = sc.next();

        if (user.equalsIgnoreCase("login")) {
            UserAuthentication.login(username, password);
        } else if (user.equalsIgnoreCase("register")) {
            UserAuthentication.registration(username, password);
            UserAuthentication.login(username, password);
        } else {
            System.out.println("Wrong choice of word.. please provide the correct choice");
        }
    }
}