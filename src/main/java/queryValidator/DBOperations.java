package queryValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DBOperations {

    public void performDBOperations(String username, List<String> list) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter SQL Query");
        String query = scanner.nextLine();
        executeQueries(query,username,list);
    }

    public void executeQueries(String query,String username,List<String> list) throws IOException {
            String[] firstWord = query.split(" ");
            switch (firstWord[0].toLowerCase()) {
                case "create":
                    CreateValidator createValidator = new CreateValidator(query);
                    createValidator.validateCreate(username, list);
                    break;
                case "insert":
                    InsertValidator insertValidator = new InsertValidator(query);
                    insertValidator.validateInsert(username, list);
                    break;
                case "drop":
                    DropValidator dropValidator=new DropValidator(query);
                    dropValidator.validateDrop(username,list);
                    break;
//            case 4:
//                break;
//            case 5:
//                break;
            }
        }

}
