package dataLogs;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DataLogs {
    public void logger(Level level, String message) {
        try {
            FileHandler handler = new FileHandler("Database.log", true);
            Logger logger = Logger.getLogger("MyLog");
            logger.addHandler(handler);
            handler.setFormatter(new SimpleFormatter());

            logger.log(level, message);
            handler.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}