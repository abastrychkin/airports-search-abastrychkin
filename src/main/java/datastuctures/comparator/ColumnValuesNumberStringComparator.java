package datastuctures.comparator;

import datastuctures.ColumnValue;

import java.util.Comparator;

public class ColumnValuesNumberStringComparator implements Comparator<ColumnValue> {
    @Override
    public  int compare(ColumnValue a, ColumnValue b) {
        String aString = a.getValue();
        String bString = b.getValue();
        double aNumber = Double.parseDouble(aString);
        double bNumber = Double.parseDouble(bString);
        return Double.compare(aNumber, bNumber);
    }
}
