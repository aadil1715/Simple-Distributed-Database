package queryValidator;

import queryParser.Create;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateValidator {

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
                Create.createParse(createTableSQL, username);
            else
                System.out.println("Please make sure the input is in standard SQL format.\n" + query + " is not valid.");
        }
    }
}

