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
    public static void createParser(Matcher createTable, String username) throws IOException {
        String tableName = createTable.group(1);
        String columns = createTable.group(2);
        String[] columnString = columns.split("\\s*,\\s*"); //Total columns in CREATE operation splitted by comma
        List<String> columnStringList = Arrays.asList(columnString); // It contains column name, datatype, and constraint of DD
        List<String> tableColumnsStringList = Arrays.asList(columnString);

        ArrayList<String> dataDictionaryColumns = new ArrayList<>();
        ArrayList<String> tableColumns = new ArrayList<>();
        ArrayList<String> dataType = new ArrayList<>();
        ArrayList<String> constraints = new ArrayList<>();
        ArrayList<String> ref_table = new ArrayList<>();

        for (int i = 0; i < columnStringList.size(); i++) {
            String[] columnType = columnStringList.get(i).split("\\s");
            if (columnType.length == 3) {
                dataDictionaryColumns.add(columnType[0]);
                dataType.add(columnType[1]);
                constraints.add(columnType[2]);
            } else if ((columnType.length == 2)) {
                dataDictionaryColumns.add(columnType[0]);
                dataType.add(columnType[1]);
            } else if (columnType.length == 6) {  //To handle primary key constraint column
                dataDictionaryColumns.add(columnType[2]);
                dataType.add(columnType[1]);
                String[] refTable = columnType[5].split("\\(");
                ref_table.add(refTable[0]);
            }
        }
        for (int i=0;i<tableColumnsStringList.size();i++) {
            if(!tableColumnsStringList.get(i).contains("PRIMARY") && !tableColumnsStringList.get(i).contains("CONSTRAINT")) {
                String[] tableColumnType = tableColumnsStringList.get(i).split("\\s+"); // separate by whitespace
                tableColumns.add(tableColumnType[0]);
            }
        }
        createDataDictionary(username,tableName,dataDictionaryColumns,dataType,constraints,ref_table);
        createTable(username, tableName, tableColumns, dataType);
    }

    public static void createDataDictionary(String username, String tableName, ArrayList<String> columnNames,
                                            ArrayList<String> colDataTypes, ArrayList<String> constraints,
                                            ArrayList<String> refTable) throws IOException {
        File dataDictionaryFile = new File("output/Data_dictionary.txt");
        File tableFile=new File("output/"+ tableName+ ".txt");

        if (!dataDictionaryFile.exists()) {
            FileWriter dataDictionary = new FileWriter(dataDictionaryFile, true);
            dataDictionary.append("TABLES").append("\t").append(" <==> ").append("\t").append("COLUMNS");
            dataDictionary.append("\n");
            dataDictionary.append(tableName).append("\t").append("<==>").append("\t");
            for (int i=0;i<columnNames.size();i++){
                if(!(i==columnNames.size()-1)) {
                    if(i<=constraints.size()-1){
                        if ((columnNames.get(i) != ("FOREIGN_KEY"))) {
                            dataDictionary.append(columnNames.get(i)).append(" ").append(colDataTypes.get(i))
                                    .append(" ").append(constraints.get(i)).append("\t").append("<->").append("\t");
                        }
                    }
                    else {
                        dataDictionary.append(columnNames.get(i)).append(" ").append(colDataTypes.get(i))
                                .append("\t").append("<->").append("\t");
                    }
                }
                else if(columnNames.get(i).equals("FOREIGN_KEY")) {
                    dataDictionary.append(columnNames.get(i)).append(" ").append("(").append("FK_COLUMN:").append(colDataTypes.get(i))
                            .append(",").append("REF_TABLE:").append(refTable.get(0)).append(")").append(";").append("\n");
                }
                else {
                    dataDictionary.append(columnNames.get(i)).append(" ").append(colDataTypes.get(i)).append(";").append("\n");
                }
            }

            dataDictionary.flush();
            log.logger(Level.INFO, "Data Dictionary created successfully");
            dataDictionary.close();
        }

        else {
         if (!tableFile.exists()) {
             FileWriter dataDictionary = new FileWriter(dataDictionaryFile, true);
             dataDictionary.append(tableName).append("\t").append("<==>").append("\t");
             for (int i=0;i<columnNames.size();i++){
                 if(!(i==columnNames.size()-1)) {
                     if(i<=constraints.size()-1){
                         if ((columnNames.get(i) != ("FOREIGN_KEY"))) {
                             dataDictionary.append(columnNames.get(i)).append(" ").append(colDataTypes.get(i))
                                     .append(" ").append(constraints.get(i)).append("\t").append("<->").append("\t");
                         }
                     }
                     else {
                         dataDictionary.append(columnNames.get(i)).append(" ").append(colDataTypes.get(i))
                                 .append("\t").append("<->").append("\t");
                     }
                 }
                 else if(columnNames.get(i).equals("FOREIGN_KEY")) {
                     dataDictionary.append(columnNames.get(i)).append(" ").append("(").append("FK_COLUMN:").append(colDataTypes.get(i))
                             .append(",").append("REF_TABLE:").append(refTable.get(0)).append(")").append(";").append("\n");
                 }
                 else {
                     dataDictionary.append(columnNames.get(i)).append(" ").append(colDataTypes.get(i)).append(";").append("\n");
                 }
             }
             dataDictionary.flush();
             dataDictionary.close();
            }

        }
    }

    public static void createTable(String username, String tableName, ArrayList<String> columnsName,
                                   ArrayList<String> colDataType) throws IOException {
        File tableFile=new File("output/"+ tableName+ ".txt");
        if(!(tableFile.exists())){
            FileWriter writeTable=new FileWriter(tableFile,true);
            for (int i=0;i<columnsName.size();i++) {
                if(!(i==columnsName.size()-1)) {
                    writeTable.append(columnsName.get(i)).append("\t").append("<->").append("\t");
                }
                else
                    writeTable.append(columnsName.get(i)).append("\n");
            }
            writeTable.flush();
            writeTable.close();
            log.logger(Level.INFO, "Table created successfully");
            System.out.println("Table: " + tableName + " created by: " + username);
        }
        else {
            System.out.println("Table cannot be created as it is already there with same name");
        }
    }
}

