package queryValidator;
import queryParser.Create;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dataLogs.DataLogs;

public class CreateValidator {
    static DataLogs log = new DataLogs();

    public static void main(String[] args) throws IOException {
        String username="Manjinder";
        validateCreate(username);
    }
    private static final Pattern CREATE_REGEX =
            Pattern.compile("(?i)(CREATE\\sTABLE\\s(\\w+)\\s?\\(((?:\\s?\\w+\\s\\w+\\(?[0-9]*\\)?,?)+)\\)\\s?;)");

    public static void validateCreate(String username) throws IOException {
        System.out.println("Enter your SQL Query");
        Scanner scanner = new Scanner(System.in);
        String query;
        while (scanner.hasNext() && !((query = scanner.nextLine()).equalsIgnoreCase("exit"))) {
            Matcher createTableSQL = CREATE_REGEX.matcher(query);

            if (createTableSQL.find())
                Create.createParser(createTableSQL, username);
            else
                log.logger(Level.WARNING, "INVALID SQL Query !!");
        }
    }
}
