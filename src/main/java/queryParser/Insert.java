package queryParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class Insert {
    public static void insertParser(Matcher insertRegex, String username) throws IOException {
        String tableName = insertRegex.group(2);  //Group to fetch table name

        String columns = insertRegex.group(3); // group to fetch column name
        String[] columnName = columns.split("\\s*,\\s*");
        List<String> columnNameList = Arrays.asList(columnName);

        String columnValues = insertRegex.group(4); //group to fetch column values
        String[] columnValue = columnValues.split("\\s*,\\s*");
        List<String> columnValueList = Arrays.asList(columnValue);

        insertData(tableName,columnNameList,columnValueList);
    }

    public static void insertData(String tablename, List<String> columnNames, List<String> columnValues) throws IOException {
        File insertInTable =new File("Output/"+tablename+".txt");
        if(insertInTable.exists()) {
            FileWriter writeInTable=new FileWriter(insertInTable);
            writeInTable.append("\n");
            for (int i = 0; i < columnValues.size(); i++) {
                if (!(i == columnValues.size() - 1)) {
                    writeInTable.append(columnValues.get(i)).append("\t").append("<->").append("\t");
                }else {
                    writeInTable.append(columnValues.get(i)).append("\n");
                }
            }
            writeInTable.flush();
            writeInTable.close();
        }

    }
}
