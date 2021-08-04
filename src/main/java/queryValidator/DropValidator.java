package queryValidator;
import dataLogs.DataLogs;
import queryParser.Drop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropValidator {
    static DataLogs log = new DataLogs();

    public static String query;
    public static FileWriter queryLogsFile;
    public DropValidator(String query, FileWriter queryLogsFile){
        this.query=query;
        this.queryLogsFile=queryLogsFile;
    }

    private static final Pattern DROP_REGEX =
            Pattern.compile("(?i)(DROP\\sTABLE\\s(\\w+);)");;

    public static void validateDrop(String username, List<String> list) throws IOException {
        Matcher dropTableSQL = DROP_REGEX.matcher(query);
        list.add(query);
        if (dropTableSQL.find()) {
            queryLogsFile.append("(").append(username).append(")=>").append("Query Entered: ").append(query).append("\n");
            Drop.dropParser(dropTableSQL, username,queryLogsFile);
        }
        else {
            queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ").append(query)
                    .append("is not appropriate SQL Query").append("\n");
            log.logger(Level.WARNING, "INVALID SQL Query !!");
        }
        queryLogsFile.flush();
        }
    }

