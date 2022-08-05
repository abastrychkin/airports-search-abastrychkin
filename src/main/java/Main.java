import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello,world!");

        int columnNumber = 5;

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
                System.out.println(tokens[index]);

            }

            // close the reader
            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
