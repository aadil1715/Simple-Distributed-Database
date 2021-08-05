package queryValidator;

import dataLogs.DataLogs;
import queryParser.Delete;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteValidator {

  static DataLogs log = new DataLogs();

  public static void main(String[] args) throws IOException {
    String username = "aadil";
    validateDelete(username);
  }
  public static String query;
  public static FileWriter queryLogsFile;
  public DeleteValidator(String query, FileWriter queryLogsFile){
    this.query=query;
    this.queryLogsFile=queryLogsFile;
  }

  private static final Pattern DELETE_REGEX =
      Pattern.compile("^DELETE(?:[^;']|(?:'[^']+'))+;?\\s*$");


  public static void validateDelete(String username) throws IOException {
      Matcher deleteTableSQL = DELETE_REGEX.matcher(query);
      if (deleteTableSQL.find()) {
        queryLogsFile.append("(").append(username).append(")=>").append("Query Entered: ").append(query).append("\n");
        Delete.parseDelete(deleteTableSQL, username,queryLogsFile);
      } else {
        queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ").append(query)
            .append("is not appropriate SQL Query").append("\n");
        log.logger(Level.WARNING, "INVALID Update SQL Query !!");
      }
    }

  }

