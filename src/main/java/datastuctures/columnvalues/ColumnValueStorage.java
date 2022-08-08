package datastuctures.columnvalues;

import datastuctures.ColumnValue;

import java.util.ArrayList;

public interface ColumnValueStorage {
    void add(ColumnValue columnValue);
    ArrayList<ColumnValue> find(String searchTemplate);
}
