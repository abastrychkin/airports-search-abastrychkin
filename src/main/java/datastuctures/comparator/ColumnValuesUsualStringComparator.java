package datastuctures.comparator;

import datastuctures.ColumnValue;

import java.util.Comparator;

public class ColumnValuesUsualStringComparator implements Comparator<ColumnValue> {
    @Override
    public  int compare(ColumnValue a, ColumnValue b) {
       return a.getValue().compareToIgnoreCase(b.getValue());
    }
}
