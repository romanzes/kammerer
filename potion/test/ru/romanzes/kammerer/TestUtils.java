package ru.romanzes.kammerer;

import java.io.InputStream;
import java.util.Scanner;

public class TestUtils {
    public static String loadResourceAsString(String fileName) {
        InputStream is = TestUtils.class.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(is, "UTF-8");
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }
}
