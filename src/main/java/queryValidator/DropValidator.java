package queryValidator;
import dataLogs.DataLogs;
import queryParser.Drop;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropValidator {
    static DataLogs log = new DataLogs();

    public static String query;
    public DropValidator(String query){
        this.query=query;
    }

    private static final Pattern DROP_REGEX =
            Pattern.compile("(?i)(DROP\\sTABLE\\s(\\w+);)");;

    public static void validateDrop(String username, List<String> list) throws IOException {
        Matcher dropTableSQL = DROP_REGEX.matcher(query);
        list.add(query);
        if (dropTableSQL.find())
            Drop.dropParser(dropTableSQL, username);
        else
            log.logger(Level.WARNING, "INVALID SQL Query !!");
        }
    }

