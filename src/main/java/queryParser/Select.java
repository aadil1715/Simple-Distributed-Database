package queryParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;

import static queryParser.Create.log;

public class Select {
  public static void selectParser(Matcher sqlQuery,String username){
    List<String> li = Arrays.asList(sqlQuery.group().split(" "));
    String tableName = li.get(3);
    String colnames = li.get(1);
    if(li.size()>4) {
      String whereCondition = li.get(5);
      String whereColumn = whereCondition.split("=")[0];
      String whereValue = whereCondition.split("=")[1];
      if (colnames.equals("*")) {
        // ADD ACTION to get all columns
      getAllRows(tableName,whereCondition);
      } else {
        String[] allcolnames = colnames.split(",");
        List<String> licolnames = Arrays.asList(allcolnames);
        getSpecificRows(tableName,licolnames,whereCondition);
      }
    }
    else{
      if (colnames.equals("*")) {
        // ADD ACTION to get all columns
        getAllRows(tableName,"None");

      } else {
        String[] allcolnames = colnames.split(",");
        List<String> licolnames = Arrays.asList(allcolnames);
        getSpecificRows(tableName,licolnames,"None");
      }
    }
  }


  public static void getSpecificRows(String tableName,
                                     List<String> liColumnNames,
                                     String whereCondition) {
    File tableFile = new File("output/" + tableName + ".txt");
    if (whereCondition.equals("None")) {
      if (tableFile.exists()) {
        try (BufferedReader br = new BufferedReader(new FileReader(tableFile))) {
          //ADD SELECTING LOGIC FOR SPECIFIC COLUMNS
          List<String> colIndexes = new ArrayList<>();
          String line = null;
          line = br.readLine();
          String[] strArr = line.replaceAll("\t", "").split("<->");
          for (int i = 0; i < liColumnNames.size(); i++) {
            if (line.contains(liColumnNames.get(i))) {
              for (int j = 0; j < strArr.length; j++) {
                if (strArr[j].equals(liColumnNames.get(i))) {
                  colIndexes.add(String.valueOf(j));
                  break;
                }
              }
            }
          }

          if (colIndexes.size() == liColumnNames.size()) {
            List<String> dataFound = new ArrayList<>();

              //ADD SELECTING LOGIC FOR SPECIFIC COLUMNS
              String line1 = null;
              Scanner sc = new Scanner(tableFile);
              while (sc.hasNext()) {
                line1 = sc.nextLine();
                String[] strArr1 = line1.replaceAll("\\s+", "").split(
                    "<->");
                for (int k = 0; k < colIndexes.size(); k++) {
                  dataFound.add(strArr1[Integer.parseInt(colIndexes.get(k))]);
                }
                System.out.println(dataFound);
                dataFound=new ArrayList<>();
              }


          } else {
            log.logger(Level.SEVERE,
                "No column found!");
          }

          //Got the column Indexes
          //System.out.println(colIndexes);

        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      String whereColumn = whereCondition.split("=")[0];
      String whereValue = whereCondition.split("=")[1];
      if (tableFile.exists()) {
        try (BufferedReader br = new BufferedReader(new FileReader(tableFile))) {
          //ADD SELECTING LOGIC FOR SPECIFIC COLUMNS
          List<String> colIndexes = new ArrayList<>();
          String line = null;
          line = br.readLine();
          int z=-1;
          String[] strArr = line.replaceAll("\t", "").split("<->");
          for (int j = 0; j < strArr.length; j++) {
            if (strArr[j].equals(whereColumn)) {
              z=j;
              break;
            }
          }
          if(z==-1){
            log.logger(Level.SEVERE,
                "No column found! for where column" );
          }
          for (int i = 0; i < liColumnNames.size(); i++) {
            if (line.contains(liColumnNames.get(i))) {
              for (int j = 0; j < strArr.length; j++) {
                if (strArr[j].equals(liColumnNames.get(i))) {
                  colIndexes.add(String.valueOf(j));
                  break;
                }
              }
            }
          }

          if (colIndexes.size() == liColumnNames.size()) {
            List<String> dataFound = new ArrayList<>();
            try (BufferedReader br1 =
                     new BufferedReader(new FileReader(tableFile))) {
              //ADD SELECTING LOGIC FOR SPECIFIC COLUMNS
              String line1 = null;
              Scanner sc = new Scanner(tableFile);
              while (sc.hasNext()) {
                line1 = sc.nextLine();
                String[] strArr1 = line1.replaceAll("\\s+", "").split(
                    "<->");
                if (z != -1) {
                  if (strArr1[z].equals(whereValue)) {
                    for (int k = 0; k <colIndexes.size(); k++) {
                      dataFound.add(strArr1[Integer.parseInt(colIndexes.get(k))]);
                    }
                  }
                }
              }
              System.out.println(dataFound);
            }
          } else {
            log.logger(Level.SEVERE,
                "No column found!");
          }

          //Got the column Indexes
          //System.out.println(colIndexes);


        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

























  public static void getAllRows(String tableName, String whereCondition) {
    File tableFile = new File("output/" + tableName + ".txt");
    if (whereCondition.equals("None")) {
      if (tableFile.exists()) {
        try (BufferedReader br = new BufferedReader(new FileReader(tableFile))) {
          String line = null;
          while ((line = br.readLine()) != null) {
            System.out.println(line);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        log.logger(Level.SEVERE, "No table found!");
      }
    }
    else{
      String whereColumn = whereCondition.split("=")[0];
      String whereValue = whereCondition.split("=")[1];
      if (tableFile.exists()) {
        try (BufferedReader br = new BufferedReader(new FileReader(tableFile))) {
          // On the first line we will get the column. Get column name by
          // splitting
          String line = null;
          int flag = 0;
          line = br.readLine();
            // COlumn exists
            if(line.contains(whereColumn)){
              String[] strArr = line.replaceAll("\t", "").split("<->");
              int j=0;
              for(int i=0;i<strArr.length;i++){
                if(strArr[i].equals(whereColumn)){
                  break;
                }
                else{
                  j++;
                }
              }

              BufferedReader br1 =
                  new BufferedReader(new FileReader(tableFile));

              String line1 = null;
              while((line1 = br1.readLine())!=null){
                line1 = br1.readLine();
                //Change regex when you get the inserted data.
                String[] strArr1 = line1.replaceAll("\\s+", "").split(
                    "<->");
                if(strArr1[j].equals(whereValue)){
                  List<String> templi = Arrays.asList(strArr1);
                  flag = 1;
                  System.out.println(templi);
                }
              }

              if(flag==1){
                System.out.println("Column Found!");
              }
              else {
                System.out.println("No data found");
              }
            }
            else{
              log.logger(Level.SEVERE,
                  "No column named" + whereColumn + "found!");
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        log.logger(Level.SEVERE, "No table found!");
      }
    }
  }
}
