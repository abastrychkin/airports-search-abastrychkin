package datastuctures.columnvaluestorage.impl;

import datastuctures.ColumnValue;
import datastuctures.columnvaluestorage.ColumnValueStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HashMapSortedArrayListColumnValueStorage implements ColumnValueStorage {
    public static final String EMPTY_STRING_KEY = "";
    private HashMap<String, ArrayList<ColumnValue>> columnValues;
    private Comparator valuesComparator;

    public HashMapSortedArrayListColumnValueStorage() {
        this.columnValues = new HashMap<>();
    }

    @Override
    public void add(ColumnValue columnValue) {
        String currentString = columnValue.getValue();

        if (!currentString.isEmpty()) {
            String firstSymbol = currentString.substring(0, 1);
            addSavingSort(firstSymbol, columnValue);
        } else {
            addSavingSort(EMPTY_STRING_KEY, columnValue);
        }
    }

    private void addSavingSort(String key, ColumnValue columnValue) {
        columnValues.computeIfAbsent(key, k -> new ArrayList<ColumnValue>()).add(columnValue);
        columnValues.get(key).sort((a, b) -> a.getValue().compareToIgnoreCase(b.getValue()));
    }

    @Override
    public List<ColumnValue> find(String searchTemplate) {
        //check searchTemplate is empty
        String firstSymbol = searchTemplate.substring(0, 1);
        //check if key exists
        ArrayList<ColumnValue> columnValues = this.columnValues.get(firstSymbol);
        // check if not found
        int index = binarySearch(columnValues, searchTemplate, 0, columnValues.size() - 1);

        List<ColumnValue> found;

        int lowBorder = findLowBorder(searchTemplate, columnValues, index);
        int highBorder = findHighBorder(searchTemplate, columnValues, index);
        found = columnValues.subList(lowBorder, highBorder);
        return found;
    }

    private static int findHighBorder(String searchTemplate, ArrayList<ColumnValue> columnValues, int index) {
        int highBorder = columnValues.size();
        for (int i = index; i < columnValues.size() - 1; i++) {
            ColumnValue current = columnValues.get(i);
            if(!current.getValue().startsWith(searchTemplate)) {
                highBorder = i;
                break;
            }
        }
        return highBorder;
    }

    private static int findLowBorder(String searchTemplate, ArrayList<ColumnValue> columnValues, int index) {
        int lowBorder = 0;
        for (int i = index; i >= 0; i--) {
            ColumnValue current = columnValues.get(i);
            if(!current.getValue().startsWith(searchTemplate)) {
                lowBorder = i + 1;
                break;
            }
        }
        return lowBorder;
    }

    public int binarySearch(ArrayList<ColumnValue> sortedArray, String searchTemplate, int low, int high) {
        int templateLength = searchTemplate.length();
        int index = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = low  + ((high - low) / 2);
            String substringWithTemplateLength = sortedArray.get(mid).getValue().substring(0, templateLength);
            if (substringWithTemplateLength.compareTo(searchTemplate) < 0) {
                low = mid + 1;
            } else if (substringWithTemplateLength.compareTo(searchTemplate) > 0) {
                high = mid - 1;
            } else if (substringWithTemplateLength.equals(searchTemplate)) {
                index = mid;
                break;
            }
        }
        return index;
    }
}
