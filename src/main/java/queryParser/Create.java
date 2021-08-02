package queryParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class Create {

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
        createTable(username, tableName, columnName, dataType);
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
            System.out.println("The table: " + tableName + " is created by: " + username);
        }
        else {
            System.out.println("The table: " + tableName + " cannot be created as it is already there with same name..");
        }
    }
}

