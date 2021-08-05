package queryValidator;

import dataLogs.DataLogs;
import queryParser.Select;
import queryParser.Update;
import queryParser.UpdateV1;

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
  public static String query;
  public static FileWriter queryLogsFile;
  public UpdateValidator(String query, FileWriter queryLogsFile){
    this.query=query;
    this.queryLogsFile=queryLogsFile;
  }

  private static final Pattern UPDATE_REGEX =
      Pattern.compile("^UPDATE(?:[^;']|(?:'[^']+'))+;?\\s*$");

  public static void validateUpdate(String username) throws IOException {
      Matcher updateTableSQL = UPDATE_REGEX.matcher(query);
//      System.out.println(selectTableSQL.find());
      if (updateTableSQL.find()) {
        //ADD ACTION
        queryLogsFile.append("(").append(username).append(")=>").append("Query Entered: ").append(query).append("\n");
        UpdateV1.parseUpdate(updateTableSQL,username,queryLogsFile);
      } else {
        queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ").append(query)
            .append("is not appropriate SQL Query").append("\n");
        log.logger(Level.WARNING, "INVALID SQL Query !!");
      }
      queryLogsFile.flush();
    }

  }
