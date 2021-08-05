package SQLDump;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataDump {

    private static final String CREATE_REGEX =
            "CREATE TABLE (\\S+)\\s*\\((.*?)\\)\\;";
    private static final String fileName = "Output/SQLDump.sql";

    public static void sqlDump(List<String> list) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
            for (String query : list) {
                if (query.matches(CREATE_REGEX)) {
                    Matcher matcher = Pattern.compile(CREATE_REGEX).matcher(query);
                    if (matcher.find()) {
                        writer.append("DROP TABLE IF EXISTS '" + matcher.group(1) + "';").append("\n");
                    }
                }
                writer.append(query).append("\n").append("\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}