package queryValidator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dataLogs.DataLogs;
import queryParser.Insert;
import java.util.List;

public class InsertValidator {
    static DataLogs log = new DataLogs();

    public static String query;
    public static FileWriter queryLogsFile;
    public InsertValidator(String query, FileWriter queryLogsFile){
        this.query=query;
        this.queryLogsFile=queryLogsFile;
    }
    private static final Pattern INSERT_REGEX =
            Pattern.compile("(?i)(INSERT\\sINTO\\s+(\\w+)+\\(([\\s\\S]+)\\)\\s+VALUES+\\(([\\s\\S]+)\\);)");

    public static void validateInsert(String username, List<String> list) throws IOException {
            Matcher insertTable = INSERT_REGEX.matcher(query);
            list.add(query);
            if (insertTable.find()) {
                queryLogsFile.append("(").append(username).append(")=>").append("Query Entered: ").append(query).append("\n");
                Insert.insertParser(insertTable, username,queryLogsFile);
            }
            else {
                queryLogsFile.append("(").append(username).append(")=>").append("Error!!.... Query: ").append(query)
                        .append("is not appropriate SQL Query").append("\n");
                log.logger(Level.WARNING, "INVALID SQL Query !!");
            }
            queryLogsFile.flush();
        }
    }
