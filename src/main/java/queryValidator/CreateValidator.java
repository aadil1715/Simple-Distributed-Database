package queryValidator;
import queryParser.Create;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dataLogs.DataLogs;
import java.util.List;

public class CreateValidator {
    static DataLogs log = new DataLogs();

    public static String query;
    public static FileWriter queryLogsFile;
    public CreateValidator(String query, FileWriter queryLogsFile){
        this.query=query;
        this.queryLogsFile=queryLogsFile;
    }

    private static final Pattern CREATE_REGEX =
            Pattern.compile("CREATE TABLE (\\S+)\\s*\\((.*?)\\)\\;");

    public static void validateCreate(String username, List<String> list) throws IOException {
        Matcher createTableSQL = CREATE_REGEX.matcher(query);
        list.add(query);
        if (createTableSQL.find()) {
            queryLogsFile.append("(").append(username).append(")=>").append("Query Entered: ").append(query).append("\n");
            Create.createParser(createTableSQL, username,queryLogsFile);
        }
        else {
            queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ").append(query)
                    .append("is not appropriate SQL Query").append("\n");
            log.logger(Level.WARNING, "INVALID SQL Query !!");
        }
        queryLogsFile.flush();
        }
    }


