package datastuctures;

public class ColumnValue {
    private String value;
    private int rowNumber;

    public ColumnValue(String value, int rowNumber) {
        this.value = value;
        this.rowNumber = rowNumber;
    }

    public String getValue() {
        return value;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public String toString() {
        return "{" + value + ", " + rowNumber + '}';
    }
}
