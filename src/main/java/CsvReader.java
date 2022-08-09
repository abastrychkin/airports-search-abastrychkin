import datastuctures.ColumnValue;
import datastuctures.columnvalues.ColumnValueStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
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

    public ColumnValueStorage getColumnValueStorage() {
        return columnValueStorage;
    }

    public void readValuesRandomAccessFile(int columnNumber) {
        try {
            String resourceFile = ClassLoader.getSystemClassLoader().getResource(fileName).getFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(resourceFile ,"r");

            String line;
            int lineCounter = 0;
            long offset = randomAccessFile.getFilePointer();
            while ((line = randomAccessFile.readLine()) != null) {
                lineCounter++;

                String[] tokens = line.split(delimiter);
                checkColumnNumber(columnNumber, tokens.length);

                int index = columnNumber - 1;
                String currentString = tokens[index];

                ColumnValue columnValue = new ColumnValue(currentString, lineCounter, offset);

                columnValueStorage.add(columnValue);
                offset = randomAccessFile.getFilePointer();
            }
            randomAccessFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkColumnNumber(int columnNumber, int tokensCount) {
        if (columnNumber > tokensCount) {
            throw new RuntimeException("Column number is more than count of columns in file. Try to run program again");
        }
    }

    public ArrayList<String> findStringsInFile(ArrayList<ColumnValue> columnValues) {
        ArrayList<String> result = new ArrayList<>();
        try {
            String resourceFile = ClassLoader.getSystemClassLoader().getResource(fileName).getFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(resourceFile ,"r");

            Iterator columnValueIterator = columnValues.iterator();

            ColumnValue currentValue;
            String line;
            while (columnValueIterator.hasNext()) {
                currentValue = (ColumnValue) columnValueIterator.next();

                randomAccessFile.seek(currentValue.getOffsetInFile());
                line = randomAccessFile.readLine();

                String formattedString = currentValue.getValue() + "[" + line + "]";
                result.add(formattedString);

            }
            randomAccessFile.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}