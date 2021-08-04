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

    public static void main(String[] args) throws IOException {
        String username="Manjinder";
        InsertValidator iv=new InsertValidator();
        iv.validateCreate(username);
    }
    private static final Pattern INSERT_REGEX =
            Pattern.compile("(?i)(INSERT\\sINTO\\s+(\\w+)+\\(([\\s\\S]+)\\)\\s+VALUES+\\(([\\s\\S]+)\\);)");

    public static void validateCreate(String username) throws IOException {
        System.out.println("Enter your SQL Query");
        Scanner scanner = new Scanner(System.in);
        String query;
        while (scanner.hasNext() && !((query = scanner.nextLine()).equalsIgnoreCase("exit"))) {
            Matcher insertTable = INSERT_REGEX.matcher(query);
            if (insertTable.find())
                Insert.insertParser(insertTable, username);
            else
                log.logger(Level.WARNING, "INVALID SQL Query !!");
        }
    }
}
