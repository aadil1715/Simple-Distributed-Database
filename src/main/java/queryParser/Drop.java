package queryParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class Drop {

    public static void dropParser(Matcher dropTable, String username) throws IOException {
        String tableName = dropTable.group(2);
    }
}
