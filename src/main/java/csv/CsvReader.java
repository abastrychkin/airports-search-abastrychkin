package csv;

import datastuctures.ColumnValue;
import datastuctures.columnvaluestorage.ColumnValueStorage;
import datastuctures.comparator.ColumnValuesNumberStringComparator;
import helper.StringHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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

    public void readValues(int columnNumber) {
        try (RandomAccessFile randomAccessFile = getRandomAccessFile()) {
            String line = randomAccessFile.readLine();
            int lineCounter = 0;
            long offset = 0;

            if (line != null) {
                getInfoFromFirstLine(columnNumber, line);
                randomAccessFile.seek(0);
            }
            while ((line = randomAccessFile.readLine()) != null) {
                lineCounter++;
                String currentColumnValue = processLine(columnNumber, line);
                ColumnValue columnValue = new ColumnValue(currentColumnValue, lineCounter, offset);
                columnValueStorage.add(columnValue);
                offset = randomAccessFile.getFilePointer();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String processLine(int columnNumber, String line) {
        String[] tokens = StringHelper.getTokens(line, delimiter);
        String columnValue = StringHelper.getCurrentString(columnNumber, tokens);
        if(ColumnValue.hasQuotesInFile()) {
            columnValue = StringHelper.removeQuotes(columnValue);
        }
        return columnValue;
    }


    private void getInfoFromFirstLine(int columnNumber, String line) {
        String[] tokens = StringHelper.getTokens(line, delimiter);
        checkColumnNumber(columnNumber, tokens.length);

        String firstColumnValue = StringHelper.getCurrentString(columnNumber, tokens);
        if (StringHelper.inQuotes(firstColumnValue)) {
            ColumnValue.setHasQuotesInFile(true);
        }
        if (StringHelper.isNumeric(firstColumnValue)){
            ColumnValue.setIsNumber(true);
        }
    }

    private void checkColumnNumber(int columnNumber, int tokensCount) {
        if (columnNumber > tokensCount) {
            throw new RuntimeException("Column number is more than count of columns in file. Try to run program again");
        }
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

    public StringBuilder getFormattedFoundedStrings(List<ColumnValue> columnValues) {
        StringBuilder result = new StringBuilder();

        if (ColumnValue.isNumber()){
            columnValues.sort(new ColumnValuesNumberStringComparator());
        }

        try (RandomAccessFile randomAccessFile = getRandomAccessFile()) {
            Iterator columnValueIterator = columnValues.iterator();

            ColumnValue currentValue;
            String line;
            boolean hasQuotes = ColumnValue.hasQuotesInFile();
            String format;
            if(hasQuotes){
                format = "\"%s\"[%s]\n";
            } else {
                format = "%s[%s]\n";
            }
            while (columnValueIterator.hasNext()) {
                currentValue = (ColumnValue) columnValueIterator.next();
                line = getLineFromFile(randomAccessFile, currentValue.getOffsetInFile());
                String currentString = currentValue.getValue();
                String formattedString = String.format(format,currentString, line );;
                result.append(formattedString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static String getLineFromFile(RandomAccessFile randomAccessFile, long offset) {
        String line = "";
        try {
            randomAccessFile.seek(offset);
            line = randomAccessFile.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }
}