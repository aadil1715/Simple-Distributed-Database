package queryParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
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
      getAllRows(tableName);

      } else {
        String[] allcolnames = colnames.split(",");
        List<String> licolnames = Arrays.asList(allcolnames);
        System.out.println(licolnames);
      }
    }
    else{
      if (colnames.equals("*")) {
        // ADD ACTION to get all columns
        getAllRows(tableName);

      } else {
        String[] allcolnames = colnames.split(",");
        List<String> licolnames = Arrays.asList(allcolnames);
        System.out.println(licolnames);
      }
    }
  }

  public static void getAllRows(String tableName){
    File tableFile=new File("output/"+ tableName+ ".txt");
    if(tableFile.exists()){
      try (BufferedReader br = new BufferedReader(new FileReader(tableFile))) {
        String line = null;
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }
      } catch (Exception e){
        e.printStackTrace();
      }
    }
    else{
      log.logger(Level.SEVERE, "No table found!");
    }
  }
}
