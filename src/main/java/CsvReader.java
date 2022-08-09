import datastuctures.ColumnValue;
import datastuctures.columnvalues.ColumnValueStorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public void readValues(int columnNumber) {
        try (RandomAccessFile randomAccessFile = getRandomAccessFile()) {
            String line = randomAccessFile.readLine();
            int lineCounter = 0;
            long offset = randomAccessFile.getFilePointer();

            if (line != null) {
                lineCounter++;

                String[] tokens = line.split(delimiter);
                checkColumnNumber(columnNumber, tokens.length);

                int index = columnNumber - 1;
                String currentString = tokens[index];

                if (currentString.startsWith("\"") & currentString.endsWith("\"") ) {
                    ColumnValue.setHasQuotesInFile(true);
                }
                if (isNumeric(currentString)){
                    ColumnValue.setIsNumber(true);
                }

                if(ColumnValue.hasQuotesInFile()) {
                    currentString = currentString.substring(1, currentString.length() -1);
                }
                ColumnValue columnValue = new ColumnValue(currentString, lineCounter, offset);

                columnValueStorage.add(columnValue);
                offset = randomAccessFile.getFilePointer();
            }

            while ((line = randomAccessFile.readLine()) != null) {
                lineCounter++;

                String[] tokens = line.split(delimiter);

                int index = columnNumber - 1;
                String currentString = tokens[index];

                if(ColumnValue.hasQuotesInFile()) {
                    currentString = currentString.substring(1, currentString.length() -1);
                }
                ColumnValue columnValue = new ColumnValue(currentString, lineCounter, offset);

                columnValueStorage.add(columnValue);
                offset = randomAccessFile.getFilePointer();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkColumnNumber(int columnNumber, int tokensCount) {
        if (columnNumber > tokensCount) {
            throw new RuntimeException("Column number is more than count of columns in file. Try to run program again");
        }
    }

    public StringBuilder findStringsInFile(List<ColumnValue> columnValues) {
        StringBuilder result = new StringBuilder();
        try (RandomAccessFile randomAccessFile = getRandomAccessFile()) {
            Iterator columnValueIterator = columnValues.iterator();

            ColumnValue currentValue;
            String line;
            boolean hasQuotes = ColumnValue.hasQuotesInFile();
            while (columnValueIterator.hasNext()) {
                currentValue = (ColumnValue) columnValueIterator.next();

                randomAccessFile.seek(currentValue.getOffsetInFile());
                line = randomAccessFile.readLine();

                String currentString = currentValue.getValue();
                String formattedString;
                if(hasQuotes){
                    formattedString = String.format("\"%s\"[%s]\n",currentString, line );
                } else {
                    formattedString = String.format("%s[%s]\n",currentString, line );;
                }
                result.append(formattedString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private RandomAccessFile getRandomAccessFile() {
        String resourceFile = ClassLoader.getSystemClassLoader().getResource(fileName).getFile();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(resourceFile ,"r");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return randomAccessFile;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }


}