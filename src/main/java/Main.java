import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
        int columnNumber = Integer.parseInt(args[0]);

        try {

            CsvReader csvReader = new CsvReader("airports.csv", ",");
            csvReader.readValues(columnValues, columnNumber);

            String searchTemplate = "\"Bo";

            long startTime = System.nanoTime();

            ArrayList<String> finded = columnValues.get(searchTemplate.substring(0,1)).stream()
                    .filter(value -> value.startsWith(searchTemplate))
                    .collect(Collectors.toCollection(ArrayList::new));

            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.

            System.out.println(finded);
            System.out.println(finded.size() + " strings");
            System.out.println(duration + "ms");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

}
