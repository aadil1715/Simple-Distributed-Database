package queryParser;

import java.io.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import dataLogs.DataLogs;

public class Drop {
    static DataLogs log = new DataLogs();
    public static void dropParser(Matcher dropTable, String username) throws IOException {
        String tableName = dropTable.group(2);
        dropDDTable(tableName);
        dropTable(tableName);
    }

    public static void dropDDTable(String tableName) throws IOException {
        String dataDictionaryPath = "Output/Data_dictionary.txt";
        File ddFile=new File(dataDictionaryPath);
        FileReader dataDictionaryFile = new FileReader(ddFile);
        BufferedReader bufferedReader = new BufferedReader(dataDictionaryFile);
        File tempDataDictionary = new File("Output/Temporary.txt");
        FileWriter fileWriter=new FileWriter(tempDataDictionary,true);
        String line;
        int count=0;
        //Iterating Line by line in file. Line in which table to be dropped is there, is not written in temporary file.
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains(tableName)) {
                int firstIndex=line.indexOf(tableName);
                int lastIndex=line.indexOf(";");
                line.substring(firstIndex,lastIndex).trim();

//                line = bufferedReader.readLine();
//                while (!line.isBlank()) {
//                    line = bufferedReader.readLine();
//                }
            }
            else{ //Write every other line in temporary file
                fileWriter.write(line);
                fileWriter.write("\n");
            }
        }
        ddFile.delete();
        tempDataDictionary.renameTo(new File("Output/Data_dictionary.txt"));
    }

    public static void dropTable(String tableName) throws FileNotFoundException {
        File tableFile = new File("output/"+tableName+".txt");
        tableFile.delete();
        log.logger(Level.INFO, "Table Dropped successfully !!!");
    }
}





