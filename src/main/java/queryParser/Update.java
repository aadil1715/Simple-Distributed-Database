package queryParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;

import static queryParser.Create.log;

public class Update {

  public static void parseUpdate(Matcher sqlQuery, String username) throws IOException {
    List<String> li = Arrays.asList(sqlQuery.group().split(" "));
      String tableName=li.get(1);
      String colNames = li.get(3);
      String whereCondition = li.get(5);
      List<String> liColnames = new ArrayList<>();
      if(colNames.contains(",")){
        liColnames = Arrays.asList(colNames.split(","));
      }else{
        liColnames = Arrays.asList(colNames);
      }
      updateOperation(tableName,liColnames,whereCondition);
  }

  public static void updateOperation(String tableName,List<String> liColNames
      ,String whereCondition) throws IOException {
    String whereColumn = whereCondition.split("=")[0];
    String whereValue = whereCondition.split("=")[1];
    File tableFile = new File("output/" + tableName + ".txt");
    String toUpdate = liColNames.get(0).split("=")[1];
    if((tableFile.exists())){
//      Scanner sc = new Scanner(tableFile);
//      //Finding col Index of where condition and column to update.
//      String line = sc.nextLine();
//      List<String> updateColIndexes = new ArrayList<>();
//      int whereColIndex = -1;
//      String[] strArr = line.replaceAll("\t", "").split("<->");
//      for(int i=0;i<strArr.length;i++){
//        if(strArr[i].equals(whereColumn)){
//          whereColIndex = i;
//          break;
//        }
//      }
//
//      if(whereColIndex==-1){
//        log.logger(Level.SEVERE,
//            "No column found! for where column" );
//      }
//      else{
//
//        for (int i = 0; i < liColNames.size(); i++) {
//          if (line.contains(liColNames.get(i))) {
//            for (int j = 0; j < strArr.length; j++) {
//              if (strArr[j].equals(liColNames.get(i))) {
//                updateColIndexes.add(String.valueOf(j));
//                break;
//              }
//            }
//          }
//        }
//        sc.close();


        Scanner sc1 = new Scanner(tableFile);
        List<String> fileContent = new ArrayList<>();
        while(sc1.hasNextLine()){
          fileContent.add(sc1.nextLine());
        }
        List<String> freshFileContent = new ArrayList<>();
        for(int j=0;j<fileContent.size();j++) {
          String temp = fileContent.get(j);
          String strArr3[] = temp.replaceAll("\t", "").split("<->");
          for (int i = 0; i < strArr3.length; i++) {
            String freshData = strArr3[i];
            freshFileContent.add(freshData);
          }
        }
        //ADD THE REPLACEMENT LOGIC
        int index = freshFileContent.indexOf(whereValue);
        freshFileContent.set(index,toUpdate);
        //ADD THE REPLACEMENT LOGIC
        System.out.println(freshFileContent);
        FileWriter fileWriter = new FileWriter(tableFile,false);
        int count=1;
        for(int j=0;j<freshFileContent.size();j++){
          if(count%4==0){
            fileWriter.append(freshFileContent.get(j)).append("\n");
          }
          else {
            fileWriter.append(freshFileContent.get(j)).append("\t").append(
                "<->").append("\t");
          }
          count++;
        }
        fileWriter.flush();

      }

    else{
      log.logger(Level.SEVERE, "No table found!");
    }
  }

}
