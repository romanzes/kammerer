package ru.romanzes.cauldron;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {
    public static String loadResourceAsString(String fileName) {
        InputStream is = Utils.class.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(is, "UTF-8");
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }
}
