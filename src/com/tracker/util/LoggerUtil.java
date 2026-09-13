package com.tracker.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void info(String message) {
        System.out.println("[INFO] [" + LocalDateTime.now().format(FORMATTER) + "] " + message);
    }

    public static void error(String message) {
        System.err.println("[ERROR] [" + LocalDateTime.now().format(FORMATTER) + "] " + message);
    }
}
