package queryValidator;

import dataLogs.DataLogs;
import queryParser.Update;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateValidator {
    static DataLogs log = new DataLogs();

    public static String query;
    public static FileWriter queryLogsFile;
    public UpdateValidator(String query, FileWriter queryLogsFile) {
        this.query = query;
        this.queryLogsFile = queryLogsFile;
    }

    private static final Pattern UPDATE_REGEX =
            Pattern.compile("^UPDATE(?:[^;']|(?:'[^']+'))+;?\\s*$");

    public static void test(){
        System.out.println("Test");
    }

    public void validateUpdate(String username) throws IOException {
        Matcher updateTableSQL = UPDATE_REGEX.matcher(query);
        if (updateTableSQL.find()) {
            queryLogsFile.append("(").append(username).append(")=>").append("Query Entered: ").append(query).append("\n");
            Update.parseUpdate(updateTableSQL,username,queryLogsFile);
        } else {
            queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ").append(query)
                    .append("is not appropriate SQL Query").append("\n");
            log.logger(Level.WARNING, "INVALID Update SQL Query !!");
        }
    }
}
