package dao;

import model.TaxTo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface TaxDao {

    Map<String, TaxTo> taxHashMap = new HashMap<>();
    // Interface fields are public and static by default. This is a slight problem as taxHashMap cannot be private.

    static Map<String, TaxTo> readFromTaxFile() throws IOException {
        // For each line in the file a taxTo object is created and added as a value to the hashmap.

        String line;
        File taxFile = new File("fileData/Data/Taxes.txt");
        FileReader fr = new FileReader(taxFile);
        BufferedReader br = new BufferedReader(fr);

        br.readLine();     // Reads (ignores) the first header line in the file.

        // Reads the remaining lines in the file
        while ((line = br.readLine()) != null) {
            // Separates the CSV file using split.
            String[] lineArr = line.split(",");

            // Converts the numerical data fields to BigDecimals.
            BigDecimal taxRate = new BigDecimal(lineArr[2]);

            // Creates a TaxTo object with the data.
            TaxTo taxTo = new TaxTo(lineArr[0], lineArr[1], taxRate);

            // Add data to the map as key:stateAbbreviation with value:TaxTo.
            taxHashMap.put(lineArr[0], taxTo);
        }
        return taxHashMap;
    }

    static TaxTo fetchTaxTo(String key) {
        // Returning a copy of the taxTo object which had the key value 'key' in the taxHashMap.
        return taxHashMap.get(key).copyTaxTo();
    }
}
