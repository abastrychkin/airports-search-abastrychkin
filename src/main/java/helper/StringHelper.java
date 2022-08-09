package helper;

public class StringHelper {
    public static boolean inQuotes(String currentString) {
        return currentString.startsWith("\"") & currentString.endsWith("\"");
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static String removeQuotes(String currentString) {
        return currentString.substring(1, currentString.length() - 1);
    }

    //regular expression for ignoring commas in quotes
    //https://www.baeldung.com/java-split-string-commas
    //paragraph 4.1
    public static String[] getTokens(String line, String delimiter) {
        return line.split(delimiter + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    public static String getCurrentString(int columnNumber, String[] tokens) {
        int index = columnNumber - 1;
        String currentString = tokens[index];
        return currentString;
    }
}