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

    void readValues(HashMap<String, ArrayList<String>> columnValues, int columnNumber) throws URISyntaxException, IOException {
        URI resource = ClassLoader.getSystemClassLoader().getResource(fileName).toURI();
        BufferedReader br = Files.newBufferedReader(Paths.get(resource));

        String line;
        while ((line = br.readLine()) != null) {

            String[] tokens = line.split(delimiter);

            int index = columnNumber - 1;

            String currentString = tokens[index];
            if (!currentString.isEmpty()) {
                columnValues.computeIfAbsent(currentString.substring(0, 1), k -> new ArrayList<String>()).add(currentString);
            } else {
                columnValues.computeIfAbsent("", k -> new ArrayList<String>()).add(currentString);
            }
        }

        br.close();
    }
}