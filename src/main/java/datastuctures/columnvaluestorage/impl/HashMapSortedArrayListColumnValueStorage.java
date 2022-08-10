package datastuctures.columnvaluestorage.impl;

import datastuctures.ColumnValue;
import datastuctures.columnvaluestorage.ColumnValueStorage;
import datastuctures.comparator.ColumnValuesUsualStringComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HashMapSortedArrayListColumnValueStorage implements ColumnValueStorage {
    public static final String EMPTY_STRING_KEY = "";
    private HashMap<String, ArrayList<ColumnValue>> columnValues;
    private Comparator valuesComparator = new ColumnValuesUsualStringComparator();

    public HashMapSortedArrayListColumnValueStorage() {
        this.columnValues = new HashMap<>();
    }

    @Override
    public void add(ColumnValue columnValue) {
        String currentString = columnValue.getValue();

        if (!currentString.isEmpty()) {
            String firstSymbol = currentString.substring(0, 1).toLowerCase();
            addSavingSort(firstSymbol, columnValue);
        } else {
            addSavingSort(EMPTY_STRING_KEY, columnValue);
        }
    }

    private void addSavingSort(String key, ColumnValue columnValue) {
        columnValues.computeIfAbsent(key, k -> new ArrayList<ColumnValue>()).add(columnValue);
        columnValues.get(key).sort(valuesComparator);
    }

    @Override
    public List<ColumnValue> find(String searchTemplate) {
        //check searchTemplate is empty
        String firstSymbol;
        if (!searchTemplate.isEmpty()) {
            firstSymbol = searchTemplate.substring(0, 1).toLowerCase();
        } else {
            firstSymbol = EMPTY_STRING_KEY;
        }

        ArrayList<ColumnValue> columnValuesStartsFromFirstSymbol;

        if(this.columnValues.containsKey(firstSymbol)) {
            columnValuesStartsFromFirstSymbol = new ArrayList<>(this.columnValues.get(firstSymbol));
        } else {
            columnValuesStartsFromFirstSymbol = new ArrayList<>();
        }
        // check if not found
        int index = binarySearch(columnValuesStartsFromFirstSymbol, searchTemplate, 0, columnValuesStartsFromFirstSymbol.size() - 1);

        List<ColumnValue> found = new ArrayList<>();
        if( index != Integer.MAX_VALUE) {
            int lowBorder = findLowBorder(searchTemplate, columnValuesStartsFromFirstSymbol, index);
            int highBorder = findHighBorder(searchTemplate, columnValuesStartsFromFirstSymbol, index);
            found = columnValuesStartsFromFirstSymbol.subList(lowBorder, highBorder);
        }
        return found;
    }

    private int findHighBorder(String searchTemplate, ArrayList<ColumnValue> columnValues, int index) {
        int templateLength = searchTemplate.length();

        int highBorder = columnValues.size();
        for (int i = index; i < columnValues.size() - 1; i++) {
            ColumnValue current = columnValues.get(i);
            String substringWithTemplateLength = current.getValue().substring(0, templateLength);
            if(!substringWithTemplateLength.equalsIgnoreCase(searchTemplate)) {
                highBorder = i;
                break;
            }
        }
        return highBorder;
    }

    private int findLowBorder(String searchTemplate, ArrayList<ColumnValue> columnValues, int index) {
        int templateLength = searchTemplate.length();

        int lowBorder = 0;
        for (int i = index; i >= 0; i--) {
            ColumnValue current = columnValues.get(i);
            String substringWithTemplateLength = current.getValue().substring(0, templateLength);
            if(!substringWithTemplateLength.equalsIgnoreCase(searchTemplate)) {
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
            String midString = sortedArray.get(mid).getValue();
            if (midString.length() < templateLength) {
                low++;
                continue;
            }
            String substringWithTemplateLength = midString.substring(0, templateLength);
            if (substringWithTemplateLength.compareToIgnoreCase(searchTemplate) < 0) {
                low = mid + 1;
            } else if (substringWithTemplateLength.compareToIgnoreCase(searchTemplate) > 0) {
                high = mid - 1;
            } else if (substringWithTemplateLength.equalsIgnoreCase(searchTemplate)) {
                index = mid;
                break;
            }
        }
        return index;
    }
}
