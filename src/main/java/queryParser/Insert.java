package queryParser;
import dataLogs.DataLogs;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;

public class Insert {

    static DataLogs log=new DataLogs();

    public static void insertParser(Matcher insertRegex, String username, FileWriter queryFileLogs) throws IOException {
        String tableName = insertRegex.group(2);  //Regex Group to fetch table name

        String columns = insertRegex.group(3); // Regex Group to fetch column name
        String[] columnName = columns.split("\\s*,\\s*");
        List<String> columnNameList = Arrays.asList(columnName);

        String columnValues = insertRegex.group(4); // Regex group to fetch column values
        String[] columnValue = columnValues.split("\\s*,\\s*");
        List<String> columnValueList = Arrays.asList(columnValue);

        try {
            if (Locks.checkLock(username, tableName)) {
                System.out.println("Table is currently locked, please try again after sometime!!");
            } else {
                Locks.setLock(username,tableName);

                // fetch primary Key column from Data Dictionary
                String dataDictionaryPath = "Output/Data_dictionary.txt";
                File ddFile = new File(dataDictionaryPath);
                FileReader dataDictionaryFile = new FileReader(ddFile);
                BufferedReader bufferedReader = new BufferedReader(dataDictionaryFile);
                String line;
                String primaryKeyColumn = null;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(tableName)) {
                        if (line.contains("PRIMARY_KEY") && !line.contains("FOREIGN_KEY")) {
                            int pkIndex = line.indexOf("PRIMARY_KEY");
                            int pkStart = pkIndex + 13;
                            int terminatorIndex = line.indexOf(";");
                            int pkEnd = terminatorIndex - 1;
                            primaryKeyColumn = line.substring(pkStart, pkEnd);
                        } else if (line.contains(("FOREIGN_KEY"))) {
                            int pkIndex = line.indexOf("PRIMARY_KEY");
                            int pkStart = pkIndex + 13;
                            int terminatorIndex = line.indexOf("FOREIGN_KEY");
                            int pkEnd = terminatorIndex - 6;
                            primaryKeyColumn = line.substring(pkStart, pkEnd);
                        }
                    }
                }
                int primaryKeyPosition = 0;
                for (int i = 0; i < columnNameList.size(); i++) {
                    if (columnNameList.get(i).equals(primaryKeyColumn)) {
                        primaryKeyPosition = i;
                    }
                }
                String primaryKeyValue = columnValueList.get(primaryKeyPosition);

                String tablePath = "Output/" + tableName + ".txt";
                FileInputStream inputStream = new FileInputStream(tablePath);
                BufferedReader bufferStream = new BufferedReader(new InputStreamReader(inputStream));
                String tableline;
                int position = 0;
                boolean isDuplicate = false;
                while ((tableline = bufferStream.readLine()) != null) {
                    String[] tableColumns = tableline.split("<->");
                    for (int i = 0; i < tableColumns.length; i++) {
                        if (tableColumns[i].contains(primaryKeyColumn))
                            position = i;
                    }
                    if (tableColumns[position].contains(primaryKeyValue))
                        isDuplicate = true;
                }
                if (isDuplicate == true) {
                    queryFileLogs.append("(").append(username).append(")=>").append("DUPLICATE error!! Cannot INSERT into : ")
                            .append(tableName).append(" table.").append("\n");
                    log.logger(Level.SEVERE, "Data cannot be inserted. DUPLICATE Error !!");
                }
                bufferStream.close();

                if (isDuplicate == false) {
                    File tableFile = new File(tablePath);
                    if (tableFile.exists()) {
                        FileWriter writeInTable = new FileWriter(tableFile, true);
                        for (int i = 0; i < columnValueList.size(); i++) {
                            if (!(i == columnValueList.size() - 1))
                                writeInTable.append(columnValueList.get(i)).append("\t").append("<->").append("\t");
                            else
                                writeInTable.append(columnValueList.get(i)).append("\n");
                        }
                        queryFileLogs.append("(").append(username).append(")=>").append("Data Inserted Successfully in : ")
                                .append(tableName).append(" table").append("\n");
                        log.logger(Level.INFO, "Data Inserted Successfully !!");
                        writeInTable.flush();
                        writeInTable.close();
                    } else {
                        queryFileLogs.append("(").append(username).append(")=>").append("Cannot INSERT into : ").append(tableName)
                                .append(" table.").append("\n");
                        log.logger(Level.WARNING, "Table does not exists !!");
                    }
                    queryFileLogs.flush();
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Locks.removeLock(username, tableName);
    }
}
