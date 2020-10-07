package ru.sberstart.util.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Log {
    private static Handler fh = null;

    static {
        try {
            fh = new FileHandler("%t/Bank_API.log");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final Logger logger = java.util.logging.Logger.getAnonymousLogger();

    static {
        logger.addHandler(fh);
    }
}
