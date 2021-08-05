package ERDGenerator;

import dataLogs.DataLogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class ERD {

    private static String fileName = "Output/ERD.txt";
    private static String outputFile = "Output/Data_Dictionary.txt";
    private static String foreignKey = "FOREIGN_KEY";

    public static void generateERD() {
        DataLogs log = new DataLogs();
        try {
            Path filePath = Paths.get(fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.createFile(filePath);

            log.logger(Level.INFO,"Generating ERD at "+fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            String line;
            boolean firstLine = true;
            while((line = bufferedReader.readLine()) != null) {
                if(!firstLine) {
                    String[] tableColumns = line.split("<==>");
                    String foreignKey_columnName = null;
                    String foreignKey_tableName = null;
                    String tableName = tableColumns[0];
                    String[] columns = tableColumns[1].split("<->");
                    for(int i=0; i<columns.length; i++) {
                        if(columns[i].contains(foreignKey)) {
                            //FOREIGN_KEY (FK_COLUMN:fk_monitor,REF_TABLE:monitor);
                            String[] foreign_key = columns[i].split(",");
                            foreignKey_columnName = foreign_key[0].split(":")[1];
                            foreignKey_tableName = foreign_key[1].split(":")[1].replace(");", "");
                            bufferedWriter.append(line).append("\n");
                            bufferedWriter.append(tableName).append("("+foreignKey_columnName+")--*----------REFERENCES------------1-->").
                                    append(foreignKey_tableName).append("\n");
                        }
                    }
                    if(foreignKey_columnName == null && foreignKey_tableName == null) {
                        bufferedWriter.append(line).append("\n");
                    }
                }
                firstLine = false;
            }
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}