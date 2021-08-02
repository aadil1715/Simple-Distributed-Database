package queryParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import dataLogs.DataLogs;
import java.util.logging.Level;

public class Create {
    static DataLogs log = new DataLogs();

    public static void createParse(Matcher createTable, String username) throws IOException {
        String tableName = createTable.group(2);
        String columns = createTable.group(3);
        String[] columnString = columns.split("\\s*,\\s*"); // separate by comma
        List<String> columnStringList = Arrays.asList(columnString); //List contains column name and datatype, eg. id int
        ArrayList<String> columnName = new ArrayList<>();
        ArrayList<String> dataType = new ArrayList<>();
        for (String s : columnStringList) {
            String[] columnType = s.split("\\s+"); // separate by whitespace
            columnName.add(columnType[0]);
            dataType.add(columnType[1]);
        }
        createDataDictionary(tableName,columnName,dataType);
        createTable(username, tableName, columnName, dataType);
    }

    public static void createDataDictionary(String tableName, ArrayList<String> columnNames,
                                            ArrayList<String> colDataTypes) throws IOException {
        File dataDictionaryFile=new File("output/Data_Dictionary.txt");
        FileWriter writeDD=new FileWriter(dataDictionaryFile,true);
        int count=1;
        if(count==1){
            writeDD.append("TABLES").append("\t").append(" || ").append("\t").append("COLUMNS");
            writeDD.append("\n");
            count++;
        }
        writeDD.append(tableName).append("\t").append("||").append("\t");
        for (int i=0;i<columnNames.size();i++){
            if(!(i==columnNames.size()-1))
                writeDD.append(columnNames.get(i)).append(" ").append(colDataTypes.get(i)).append("\t")
                        .append("->").append("\t");
            else
                writeDD.append(columnNames.get(i)).append("").append(colDataTypes.get(i)).append("\n");
        }

        if(!dataDictionaryFile.exists()) {
            log.logger(Level.INFO, "Data Dictionary created successfully");
        }
        else {
            log.logger(Level.WARNING, "Data Dictionary already exists");
        }
        writeDD.flush();
        writeDD.close();
    }

    public static void createTable(String username, String tableName, ArrayList<String> columnsName,
                                   ArrayList<String> colDataType) throws IOException {
        File tableFile=new File("output/"+ tableName+ ".txt");
        if(!(tableFile.exists())){
            FileWriter writeTable=new FileWriter(tableFile,true);
            for (int i=0;i<columnsName.size();i++) {
                if(!(i==columnsName.size()-1)) {
                    writeTable.append(columnsName.get(i)).append("\t").append("||").append("\t");
                }
                else
                    writeTable.append(columnsName.get(i));
            }
            writeTable.flush();
            writeTable.close();
            log.logger(Level.INFO, "Table created successfully");
            System.out.println("Table: " + tableName + " created by: " + username);
        }
        else {
            log.logger(Level.SEVERE, "Table cannot be created as it is already there with same name");
        }
    }
}

