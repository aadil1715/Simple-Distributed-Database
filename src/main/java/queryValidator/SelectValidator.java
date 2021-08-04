package queryValidator;
import queryParser.Insert;
import queryParser.Select;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dataLogs.DataLogs;
public class SelectValidator {
  static DataLogs log = new DataLogs();

  public static void main(String[] args) throws IOException {
    String username = "aadil";
    validateSelect(username);
  }

  private static final Pattern SELECT_REGEX =
      Pattern.compile("^SELECT(?:[^;']|(?:'[^']+'))+;?\\s*$");

  public static void validateSelect(String username) throws IOException {
    System.out.println("Enter your SQL Query");
    Scanner scanner = new Scanner(System.in);
    String query;
    while (scanner.hasNext() && !((query = scanner.nextLine()).equalsIgnoreCase("exit"))) {
      Matcher selectTableSQL = SELECT_REGEX.matcher(query);
//      System.out.println(selectTableSQL.find());
      if (selectTableSQL.find()) {
        //ADD ACTION
        Select.selectParser(selectTableSQL,username);
      }
      else {
        log.logger(Level.WARNING, "INVALID SQL Query !!");
      }
    }


  }

}

