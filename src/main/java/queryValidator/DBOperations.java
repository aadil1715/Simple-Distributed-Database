package queryValidator;

import java.io.File;
import java.io.FileWriter;
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
        File queryLogs=new File("Output/Query_Logs.txt");
        FileWriter queryLogsFile = new FileWriter(queryLogs,true);

        switch (firstWord[0].toLowerCase()) {
            case "create":
                CreateValidator createValidator = new CreateValidator(query,queryLogsFile);
                createValidator.validateCreate(username, list);
                break;
            case "insert":
                InsertValidator insertValidator = new InsertValidator(query,queryLogsFile);
                insertValidator.validateInsert(username, list);
                break;
            case "drop":
                DropValidator dropValidator=new DropValidator(query,queryLogsFile);
                dropValidator.validateDrop(username);
                break;
            case "select":
                SelectValidator selectValidator=new SelectValidator(query,queryLogsFile);
                selectValidator.validateSelect(username);
                break;
            case "update":
                UpdateValidator updateValidator=new UpdateValidator(query,queryLogsFile);
                updateValidator.validateUpdate(username);
                break;
            case "delete":
                DeleteValidator deleteValidator = new DeleteValidator(query,
                    queryLogsFile);
                deleteValidator.validateDelete(username);

            }
        }

}
