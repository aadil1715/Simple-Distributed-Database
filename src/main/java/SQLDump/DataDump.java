package SQLDump;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataDump {

    private static String fileName = "Output/SQLDump.sql";
    private static final String CREATE_REGEX =
            "CREATE TABLE (\\S+)\\s*\\((.*?)\\)\\;";

    public static void sqlDump(List<String> list) {
        try {
            Path filePath = Paths.get(fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.createFile(filePath);

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (String query : list) {
                if(query.matches(CREATE_REGEX)) {
                    Matcher matcher = Pattern.compile(CREATE_REGEX).matcher(query);
                    if (matcher.find()) {
                        writer.append("DROP TABLE IF EXISTS '" + matcher.group(1) + "';").append("\n");
                    }
                }
                writer.append(query).append("\n").append("\n");
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
