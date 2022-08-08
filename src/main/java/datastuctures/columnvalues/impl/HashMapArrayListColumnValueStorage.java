package datastuctures.columnvalues.impl;

import datastuctures.ColumnValue;
import datastuctures.columnvalues.ColumnValueStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HashMapArrayListColumnValueStorage implements ColumnValueStorage {
    private HashMap<String, ArrayList<ColumnValue>> columnValues;

    public HashMapArrayListColumnValueStorage() {
        this.columnValues = new HashMap<>();
    }

    @Override
    public void add(ColumnValue columnValue) {
        String currentString = columnValue.getValue();
        if (!currentString.isEmpty()) {
            columnValues.computeIfAbsent(currentString.substring(0, 1), k -> new ArrayList<ColumnValue>()).add(columnValue);
        } else {
            columnValues.computeIfAbsent("", k -> new ArrayList<ColumnValue>()).add(columnValue);
        }
    }

    @Override
    public ArrayList<ColumnValue> find(String searchTemplate) {
        ArrayList<ColumnValue> found = columnValues.get(searchTemplate.substring(0,1)).stream()
                .filter(value -> value.getValue().startsWith(searchTemplate))
                .collect(Collectors.toCollection(ArrayList::new));
        return found;
    }
}
