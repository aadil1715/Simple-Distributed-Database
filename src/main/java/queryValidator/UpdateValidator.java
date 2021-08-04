package queryValidator;

import dataLogs.DataLogs;
import queryParser.Select;
import queryParser.Update;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateValidator {

  static DataLogs log = new DataLogs();

  public static void main(String[] args) throws IOException {
    String username = "aadil";
    validateUpdate(username);
  }
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
        Update.parseUpdate(updateTableSQL,username);
      } else {
        log.logger(Level.WARNING, "INVALID Update SQL Query !!");
      }
    }

  }
}