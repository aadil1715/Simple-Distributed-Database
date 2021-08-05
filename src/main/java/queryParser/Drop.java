package queryParser;

import java.io.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import dataLogs.DataLogs;

public class Drop {
    static DataLogs log = new DataLogs();
    public static void dropParser(Matcher dropTable, String username,FileWriter queryFileLogs) throws IOException {
        String tableName = dropTable.group(2);
        dropDDTable(tableName,username,queryFileLogs);
        dropTable(tableName,username,queryFileLogs);
    }

    public static void dropDDTable(String tableName, String username, FileWriter queryFileLogs) throws IOException {
        try {
            if (Locks.checkLock(username, tableName)) {
                System.out.println("Table is currently locked, please try again after sometime!!");
            } else {
                Locks.setLock(username, tableName);
                String dataDictionaryPath = "Output/Data_dictionary.txt";
                File ddFile = new File(dataDictionaryPath);
                FileReader dataDictionaryFile = new FileReader(ddFile);
                BufferedReader bufferedReader = new BufferedReader(dataDictionaryFile);
                File tempDataDictionary = new File("Output/Temporary.txt");
                FileWriter fileWriter = new FileWriter(tempDataDictionary, true);
                String line;
                int count = 0;
                //Iterating Line by line in file. Line in which table to be dropped is there, is not written in temporary file.
                while ((line = bufferedReader.readLine()) != null) {
                    String currentLine = line;
                    if (line.contains(tableName)) {
                        int firstIndex = line.indexOf(tableName);
                        int lastIndex = line.indexOf(";");
                        String lineToDrop = line.substring(firstIndex, lastIndex);
                        if (currentLine.equals(lineToDrop))
                            continue;
                    }
                    // Write every other line in temporary file
                    else {
                        fileWriter.write(currentLine);
                        fileWriter.write("\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
                ddFile.delete();
                tempDataDictionary.renameTo(new File("Output/Data_dictionary.txt"));
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Locks.removeLock(username,tableName);
    }

    public static void dropTable(String tableName,String username,FileWriter queryFileLogs) throws IOException {
        try {
            if (Locks.checkLock(username, tableName)) {
                System.out.println("Table is currently locked, please try again after sometime!!");
            } else {
                Locks.setLock(username, tableName);
                File tableFile = new File("output/" + tableName + ".txt");
                boolean isTableDeleted = tableFile.delete();
                if (isTableDeleted) {
                    queryFileLogs.append("(").append(username).append(")=> ").append("TABLE: ").append(tableName)
                            .append(" is Dropped.").append("\n");
                    log.logger(Level.INFO, "Table Dropped successfully !!!");
                } else {
                    queryFileLogs.append("(").append(username).append(")=> ").append("TABLE: ").append(tableName)
                            .append(" does not exist.").append("\n");
                    log.logger(Level.WARNING, "Table Does not exist !!!");
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Locks.removeLock(username, tableName);
    }
}





