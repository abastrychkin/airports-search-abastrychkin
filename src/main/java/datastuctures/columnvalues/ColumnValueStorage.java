package datastuctures.columnvalues;

import datastuctures.ColumnValue;

import java.util.ArrayList;
import java.util.List;

public interface ColumnValueStorage {
    void add(ColumnValue columnValue);
    List<ColumnValue> find(String searchTemplate);
}
