package queryValidator;

import dataLogs.DataLogs;
import queryParser.Delete;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteValidator {

  static DataLogs log = new DataLogs();

  public static void main(String[] args) throws IOException {
    String username = "aadil";
    validateDelete(username);
  }

  private static final Pattern DELETE_REGEX =
      Pattern.compile("^DELETE(?:[^;']|(?:'[^']+'))+;?\\s*$");


  public static void validateDelete(String username) throws IOException {
    System.out.println("Enter your SQL Query");
    Scanner scanner = new Scanner(System.in);
    String query;
    while (scanner.hasNext() && !((query = scanner.nextLine()).equalsIgnoreCase("exit"))) {
      Matcher deleteTableSQL = DELETE_REGEX.matcher(query);
      if (deleteTableSQL.find()) {
        Delete.parseDelete(deleteTableSQL, username);
      } else {
        log.logger(Level.WARNING, "INVALID Update SQL Query !!");
      }
    }

  }

}
