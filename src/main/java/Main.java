import datastuctures.ColumnValue;
import datastuctures.columnvalues.ColumnValueStorage;
import datastuctures.columnvalues.impl.HashMapArrayListColumnValueStorage;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static final int MIN_POSSIBLE_COLUMN_NUMBER_VALUE = 1;
    private static final long NANOSECONDS_IS_MILLISECOND = 1_000_000l;
    private static int columnNumber;
    private static String searchTemplate = "";

    public static void main(String[] args) {
        parseArgs(args);

        ColumnValueStorage columnValueStorage = new HashMapArrayListColumnValueStorage();

        CsvReader csvReader = new CsvReader("airports.csv", ",", columnValueStorage);
        csvReader.readValues(columnNumber);

        Scanner in = new Scanner(System.in);
        enterNewTemplate(in);
        while (!searchTemplate.equals("!quit")){
            long startTime = System.nanoTime();

            ArrayList<ColumnValue> found = columnValueStorage.find(searchTemplate);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
            long durationMs = (endTime - startTime)/NANOSECONDS_IS_MILLISECOND;

            System.out.println(found);
            System.out.println("Row found: " + found.size());
            System.out.println("Search time: " + durationMs + "ms, "  + duration + "ns");

            enterNewTemplate(in);
        }

        in.close();
    }
    private static void parseArgs(String[] args) {
        try {
            columnNumber = Integer.parseInt(args[0]);
            if (columnNumber < MIN_POSSIBLE_COLUMN_NUMBER_VALUE) {
                throw new RuntimeException("Column number is less or equal 0. Try to run program again. ");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void enterNewTemplate(Scanner in) {
        System.out.println("Enter line: ");
        searchTemplate = in.nextLine();
    }
}
