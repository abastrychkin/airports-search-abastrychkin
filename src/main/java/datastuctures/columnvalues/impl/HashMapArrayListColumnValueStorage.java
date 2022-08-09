package datastuctures.columnvalues.impl;

import datastuctures.ColumnValue;
import datastuctures.columnvalues.ColumnValueStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HashMapArrayListColumnValueStorage implements ColumnValueStorage {
    private HashMap<String, ArrayList<ColumnValue>> columnValues;
    private Comparator valuesComparator;


    public HashMapArrayListColumnValueStorage() {
        this.columnValues = new HashMap<>();
    }

    @Override
    public void add(ColumnValue columnValue) {
        String currentString = columnValue.getValue();


        if (!currentString.isEmpty()) {
            columnValues.computeIfAbsent(currentString.substring(0, 1), k -> new ArrayList<ColumnValue>()).add(columnValue);
            columnValues.get(currentString.substring(0, 1)).sort((a,b) -> a.getValue().compareToIgnoreCase(b.getValue()));
        } else {
            columnValues.computeIfAbsent("", k -> new ArrayList<ColumnValue>()).add(columnValue);
            columnValues.get("").sort((a,b) -> a.getValue().compareToIgnoreCase(b.getValue()));
        }
    }

    @Override
    public List<ColumnValue> find(String searchTemplate) {
        //check searchTemplate is empty
        ArrayList<ColumnValue> columnValues1 = columnValues.get(searchTemplate.substring(0,1));
        int index = binarySearchIteratively(columnValues1, searchTemplate, 0, columnValues1.size() - 1);

        List<ColumnValue> found1 = new ArrayList<ColumnValue>();

        int lowBorder = 0;
        for (int i = index; i >= 0; i--) {
            ColumnValue current = columnValues1.get(i);
            if(!current.getValue().startsWith(searchTemplate)) {
                lowBorder = i + 1;
                break;
            }
        }

        int highBorder = columnValues1.size();
        for (int i = index; i < columnValues1.size() - 1; i++) {
            ColumnValue current = columnValues1.get(i);
            if(!current.getValue().startsWith(searchTemplate)) {
                highBorder = i;
                break;
            }
        }

        found1 = columnValues1.subList(lowBorder, highBorder);

//        ArrayList<ColumnValue> found = columnValues.get(searchTemplate.substring(0,1)).stream()
//                .filter(value -> value.getValue().startsWith(searchTemplate))
//                .collect(Collectors.toCollection(ArrayList::new));
        return found1;
    }


    public int binarySearchIteratively(ArrayList<ColumnValue> sortedArray, String searchTemplate, int low, int high) {
        int templateLength = searchTemplate.length();
        int index = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = low  + ((high - low) / 2);
            if (sortedArray.get(mid).getValue().substring(0, templateLength).compareTo(searchTemplate) < 0) {
                low = mid + 1;
            } else if (sortedArray.get(mid).getValue().substring(0, templateLength).compareTo(searchTemplate) > 0) {
                high = mid - 1;
            } else if (sortedArray.get(mid).getValue().startsWith(searchTemplate)) {
                index = mid;
                break;
            }
        }
        return index;
    }
}
