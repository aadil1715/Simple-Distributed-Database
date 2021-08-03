package queryValidator;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dataLogs.DataLogs;
import queryParser.Insert;

public class InsertValidator {
    static DataLogs log = new DataLogs();

    private static final Pattern INSERT_REGEX =
            Pattern.compile("(?i)(INSERT\\sINTO\\s+(\\w+)\\s+\\(([\\s\\S]+)\\)\\s+VALUES\\s+\\(([\\s\\S]+)\\);)");

    public static void validateCreate(String username) throws IOException {
        System.out.println("Enter your SQL Query");
        Scanner scanner = new Scanner(System.in);
        String query;
        while (scanner.hasNext() && !((query = scanner.nextLine()).equalsIgnoreCase("exit"))) {
            Matcher insertTableSQL = INSERT_REGEX.matcher(query);

            if (insertTableSQL.find())
                Insert.insertParser(insertTableSQL, username);
            else
                log.logger(Level.WARNING, "INVALID SQL Query !!");
        }
    }

}
