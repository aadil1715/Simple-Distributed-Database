package queryValidator;
import queryParser.Insert;
import queryParser.Select;

import java.io.FileWriter;
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

  public static String query;
  public static FileWriter queryLogsFile;
  public SelectValidator(String query, FileWriter queryLogsFile){
    this.query=query;
    this.queryLogsFile=queryLogsFile;
  }

  private static final Pattern SELECT_REGEX =
      Pattern.compile("^SELECT(?:[^;']|(?:'[^']+'))+;?\\s*$");

  public static void validateSelect(String username) throws IOException {
      Matcher selectTableSQL = SELECT_REGEX.matcher(query);
      if (selectTableSQL.find()) {
        queryLogsFile.append("(").append(username).append(")=>").append("Query Entered: ").append(query).append("\n");
        Select.selectParser(selectTableSQL,username,queryLogsFile);
      }
      else {
        queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ").append(query)
                .append("is not appropriate SQL Query").append("\n");
        log.logger(Level.WARNING, "INVALID SQL Query !!");
      }
      queryLogsFile.flush();
    }
  }



