import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello,world!");



        //ArrayList<String> columnValues = new ArrayList<>();
        HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
        int columnNumber = 2;

        try {
            // CSV file delimiter
            String DELIMITER = ",";

            // create a reader
            URI resource = ClassLoader.getSystemClassLoader().getResource("airports.csv").toURI();

            BufferedReader br = Files.newBufferedReader(Paths.get(resource));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);

                // TODO: do something here with the data

                // print all tokens
//                for (String token : tokens) {
//                    System.out.print(token + " ");
//                }
                int index = columnNumber - 1;
//                System.out.println(tokens[index]);

                String currentString = tokens[index].substring(1);
                if (!currentString.isEmpty()) {
                    columnValues.computeIfAbsent(currentString.substring(0,1), k -> new ArrayList<>()).add(currentString);
                } else {
                    columnValues.computeIfAbsent("", k -> new ArrayList<>()).add(currentString);
                }


            }

            // close the reader
            br.close();

           // System.out.println(columnValues);

            String searchTemplate = "Bo";

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
