package queryParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class Insert {
    public static void insertParser(Matcher insertTable, String username) throws IOException {
        String tableName = insertTable.group(2);
        String keys = insertTable.group(3);
        String[] columnName = keys.split("\\s*,\\s*");
        List<String> columnNameList = Arrays.asList(columnName);
        // Separate keys into a arraylist
        String values = insertTable.group(4);
        String[] columnValue = values.split("\\s*,\\s*");
        List<String> columnValueList = Arrays.asList(columnValue);
    }
}
