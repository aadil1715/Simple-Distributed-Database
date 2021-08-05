package queryParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;

import static queryParser.Insert.log;

public class Delete {

  public static void parseDelete(Matcher sqlQuery, String username,
                                 FileWriter queryLogsFile) throws IOException {
    List<String> li = Arrays.asList(sqlQuery.group().split(" "));
    String tableName = li.get(2);
    String whereCondition = li.get(4);
    deleteOperation(tableName, whereCondition,username,queryLogsFile);
  }

  public static void deleteOperation(String tableName, String whereCondition,
                                     String username, FileWriter queryLogsFile
                                     ) throws IOException {
    String whereColumn = whereCondition.split("=")[0];
    String whereValue = whereCondition.split("=")[1];
    File tableFile = new File("output/" + tableName + ".txt");
    if ((tableFile.exists())) {
      List<List<String>> fileData = new ArrayList<>();
      List<List<String>> fileDataToDelete = new ArrayList<>();
      Scanner sc1 = new Scanner(tableFile);
      while (sc1.hasNextLine()) {
        List<String> fileContent = new ArrayList<>();
        String[] cells = sc1.nextLine().replaceAll("\t", "").split("<->");
        for (String cell : cells) {
          fileContent.add(cell);
        }
        fileData.add(fileContent);
      }
      Scanner sc = new Scanner(tableFile);
      String line = sc.nextLine();
      List<String> updateColIndexes = new ArrayList<>();
      int whereColIndex = -1;
      String[] strArr = line.replaceAll("\t", "").split("<->");
      for (int i = 0; i < strArr.length; i++) {
        if (strArr[i].equals(whereColumn)) {
          whereColIndex = i;
          break;
        }
      }

      if (whereColIndex == -1) {
        log.logger(Level.SEVERE,
            "No column found! for where column");
        queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ")
            .append("No column found! for where column").append("\n");
      } else {
        for (int a = 1; a < fileData.size(); a++) {
          if (fileData.get(a).get(whereColIndex).equals(whereValue)) {
            fileDataToDelete.add(fileData.get(a));
          }
        }
        fileData.removeAll(fileDataToDelete);
      }
      FileWriter fileWriter = new FileWriter(tableFile, false);
      for (List<String> rowData : fileData) {
        for (int a = 0; a < rowData.size(); a++) {
          if (a == rowData.size() - 1) {
            fileWriter.append(rowData.get(a)).append("\n");
          } else {
            fileWriter.append(rowData.get(a)).append("\t").append(
                "<->").append("\t");
          }
        }
      }
      fileWriter.flush();

      System.out.println("Deleted Records Successfully");
      queryLogsFile.append("(").append(username).append(")=>").append("Query " +
          "executed successfully " +
          ": ")
          .append("Deleted records successfully!").append("\n");
      queryLogsFile.flush();
    }
  }
}
