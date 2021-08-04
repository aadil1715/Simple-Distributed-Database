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
//        File tableFile = new File("output/" + tableName + ".txt");
//        Scanner sc1 = new Scanner(tableFile);
//        List<String> fileContent = new ArrayList<>();
//        while(sc1.hasNextLine()){
//          fileContent.add(sc1.nextLine());
//        }
//        List<String> freshFileContent = new ArrayList<>();
//        for(int j=0;j<fileContent.size();j++) {
//          String temp = fileContent.get(j);
//          String strArr[] = temp.replaceAll("\t", "").split("<->");
//          for (int i = 0; i < strArr.length; i++) {
//            String freshData = strArr[i];
//            freshFileContent.add(freshData);
//          }
//        }
//        int index = freshFileContent.indexOf("1");
//        freshFileContent.set(index,"4");
//        System.out.println(freshFileContent);
//        FileWriter fileWriter = new FileWriter(tableFile,false);
//        int count=1;
//        for(int j=0;j<freshFileContent.size();j++){
//          if(count%4==0){
//            fileWriter.append(freshFileContent.get(j)).append("\n");
//          }
//          else {
//            fileWriter.append(freshFileContent.get(j)).append("\t").append(
//                "<->").append("\t");
//          }
//          count++;
//        }
//        fileWriter.flush();
      } else {
        log.logger(Level.WARNING, "INVALID Update SQL Query !!");
      }
    }

  }
}