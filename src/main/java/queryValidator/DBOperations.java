package queryValidator;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DBOperations {

    public void performDBOperations(String username, List<String> list) throws IOException {
        Scanner scanner =new Scanner(System.in);
        System.out.println("***************");
        System.out.println("1.CREATE");
        System.out.println("2.INSERT");
        System.out.println("3.DROP");
        System.out.println("4.SELECT");
        System.out.println("5.UPDATE");
        System.out.println("6.DELETE");
        System.out.println("***************");
        int input=scanner.nextInt();
        switch (input){
            case 1:
                CreateValidator.validateCreate(username,list);
                break;
            case 2:
                InsertValidator.validateInsert(username,list);
                break;
            case 3:
                DropValidator.validateDrop(username,list);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }
}
