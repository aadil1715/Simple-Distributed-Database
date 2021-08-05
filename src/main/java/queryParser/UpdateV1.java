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

public class UpdateV1 {

  public static void parseUpdate(Matcher sqlQuery, String username) throws IOException {
    List<String> li = Arrays.asList(sqlQuery.group().split(" "));
    String tableName = li.get(1);
    String colNames = li.get(3);
    String whereCondition = li.get(5);
    List<String> liColnames = new ArrayList<>();
    if (colNames.contains(",")) {
      liColnames = Arrays.asList(colNames.split(","));
    } else {
      liColnames = Arrays.asList(colNames);
    }
    updateOperation(tableName, liColnames, whereCondition);
  }

  public static void updateOperation(String tableName, List<String> liColNames
      , String whereCondition) throws IOException {
    String whereColumn = whereCondition.split("=")[0];
    String whereValue = whereCondition.split("=")[1];
    File tableFile = new File("output/" + tableName + ".txt");
    String columntoUpdate = liColNames.get(0).split("=")[0];
    String toUpdate = liColNames.get(0).split("=")[1];
    if ((tableFile.exists())) {
      Scanner sc = new Scanner(tableFile);
      List<List<String>> fileData = new ArrayList<>();

      Scanner sc1 = new Scanner(tableFile);

      while (sc1.hasNextLine()) {
        List<String> fileContent = new ArrayList<>();
        String[] cells = sc1.nextLine().replaceAll("\t", "").split("<->");
        for (String cell : cells) {
          fileContent.add(cell);
        }
        fileData.add(fileContent);
      }


      //Finding col Index of where condition and column to update.
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
      } else {

        for (int i = 0; i < liColNames.size(); i++) {
          String columntoUpdate1 = liColNames.get(i).split("=")[0];
          if (line.contains(columntoUpdate1)) {
            for (int j = 0; j < strArr.length; j++) {
              if (strArr[j].equals(columntoUpdate1)) {
                updateColIndexes.add(String.valueOf(j));
                break;
              }
            }
          }
        }
        sc.close();
      }

      for (int a = 1; a < fileData.size(); a++) {
        if (fileData.get(a).get(whereColIndex).equals(whereValue)) {
          for (int i = 0; i < updateColIndexes.size(); i++) {
            List<String> x = fileData.get(a);
            x.set(Integer.parseInt(updateColIndexes.get(i)),
                liColNames.get(i).split("=")[1]);
            fileData.set(a, x);
          }
        }
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
      System.out.println("Updated records Successfully");
    }


  }
}
