package queryValidator;

import dataLogs.DataLogs;
import queryParser.Select;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateValidator {

  static DataLogs log = new DataLogs();
  private static final Pattern UPDATE_REGEX =
      Pattern.compile("^UPDATE(?:[^;']|(?:'[^']+'))+;?\\s*$");

  public static void validateUpdate(String username) throws IOException {
    System.out.println("Enter your SQL Query");
    Scanner scanner = new Scanner(System.in);
    String query;
    while (scanner.hasNext() && !((query = scanner.nextLine()).equalsIgnoreCase("exit"))) {
      Matcher updateTableSQL = UPDATE_REGEX.matcher(query);
//      System.out.println(selectTableSQL.find());
      if (updateTableSQL.find()) {
        //ADD ACTION

      } else {
        log.logger(Level.WARNING, "INVALID Update SQL Query !!");
      }
    }

  }
}