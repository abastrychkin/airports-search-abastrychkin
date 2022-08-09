package datastuctures;

public class ColumnValue {

    private static boolean isNumber;
    private static boolean hasQuotesInFile;

    private String value;
    private int rowNumber;
    long offsetInFile;

    public ColumnValue(String value, int rowNumber, long offsetInFile) {
        this.value = value;
        this.rowNumber = rowNumber;
        this.offsetInFile = offsetInFile;
    }

    public String getValue() {
        return value;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public long getOffsetInFile() {
        return offsetInFile;
    }

    public static boolean isNumber() {
        return isNumber;
    }
    public static boolean hasQuotesInFile() {
        return hasQuotesInFile;
    }

    public static void setIsNumber(boolean isNumber) {
        ColumnValue.isNumber = isNumber;
    }

    public static void setHasQuotesInFile(boolean hasQuotesInFile) {
        ColumnValue.hasQuotesInFile = hasQuotesInFile;
    }

    @Override
    public String toString() {
        return "{" + value + ", " + rowNumber + '}';
    }
}
