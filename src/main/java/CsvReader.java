import datastuctures.ColumnValue;
import datastuctures.columnvalues.ColumnValueStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class CsvReader {
    private String delimiter;
    private String fileName;
    private ColumnValueStorage columnValueStorage;

    public CsvReader(String fileName, String delimiter, ColumnValueStorage columnValueStorage) {
        this.fileName = fileName;
        this.delimiter = delimiter;
        this.columnValueStorage = columnValueStorage;
    }

    public void readValues(int columnNumber) {

        try {
            URI resource = ClassLoader.getSystemClassLoader().getResource(fileName).toURI();
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(resource));

            String line;
            int lineCounter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineCounter++;

                String[] tokens = line.split(delimiter);
                checkColumnNumber(columnNumber, tokens.length);

                int index = columnNumber - 1;
                String currentString = tokens[index];
                ColumnValue columnValue = new ColumnValue(currentString, lineCounter);

                columnValueStorage.add(columnValue);
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

    public ColumnValueStorage getColumnValueStorage() {
        return columnValueStorage;
    }

    public ArrayList<String> findStringsInFile(ArrayList<ColumnValue> columnValues) {
        ArrayList<String> result = new ArrayList<>();
        try {
            URI resource = ClassLoader.getSystemClassLoader().getResource(fileName).toURI();
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(resource));

            Iterator columnValueIterator = columnValues.iterator();

            ColumnValue currentValue;
            if (columnValueIterator.hasNext()) {
                currentValue = (ColumnValue) columnValueIterator.next();

                String line;
                int lineCounter = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    lineCounter++;

                    if (lineCounter == currentValue.getRowNumber()) {
                        String formattedString = currentValue.getValue() + "[" + line + "]";
                        result.add(formattedString);
                        if (columnValueIterator.hasNext()) {
                            currentValue = (ColumnValue) columnValueIterator.next();
                        }
                    }
                }
            }
            bufferedReader.close();

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return result;

    }
}