package datastuctures;

public class ColumnValue {
    private String value;
    private int rowNumber;
    private long offsetInFile;

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
}
