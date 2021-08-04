package queryValidator;
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
    public InsertValidator(String query){
        this.query=query;

    }
    private static final Pattern INSERT_REGEX =
            Pattern.compile("(?i)(INSERT\\sINTO\\s+(\\w+)+\\(([\\s\\S]+)\\)\\s+VALUES+\\(([\\s\\S]+)\\);)");

    public static void validateInsert(String username, List<String> list) throws IOException {
//        System.out.println("Enter your SQL Query");
//        Scanner scanner = new Scanner(System.in);
//        String query;
//        while (scanner.hasNext() && !((query = scanner.nextLine()).equalsIgnoreCase("exit"))) {
            Matcher insertTable = INSERT_REGEX.matcher(query);
            list.add(query);
            if (insertTable.find())
                Insert.insertParser(insertTable, username);
            else
                log.logger(Level.WARNING, "INVALID SQL Query !!");
        }
    }
