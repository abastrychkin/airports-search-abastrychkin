import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvReader {
    private String delimiter;
    private String fileName;
    public CsvReader(String fileName, String delimiter) {
        this.fileName = fileName;
        this.delimiter = delimiter;
    }

    public void readValues(HashMap<String, ArrayList<String>> columnValues, int columnNumber) {

        try {
            URI resource = ClassLoader.getSystemClassLoader().getResource(fileName).toURI();
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(resource));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] tokens = line.split(delimiter);

                checkColumnNumber(columnNumber, tokens.length);

                int index = columnNumber - 1;

                String currentString = tokens[index];
                if (!currentString.isEmpty()) {
                    columnValues.computeIfAbsent(currentString.substring(0, 1), k -> new ArrayList<String>()).add(currentString);
                } else {
                    columnValues.computeIfAbsent("", k -> new ArrayList<String>()).add(currentString);
                }
            }

            bufferedReader.close();

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    private void checkColumnNumber(int columnNumber, int tokensCount) {
        if (columnNumber > tokensCount) {
            throw new RuntimeException("Column number is more than count of columns in file. Try to run program again");
        }
    }
}