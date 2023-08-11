package dao;

import model.TaxTo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TaxDaoImpl implements TaxDao {

    Map<String, TaxTo> taxHashMap;

    public TaxDaoImpl() {
        taxHashMap = new HashMap<>();
    }

    @Override
    public Map<String, TaxTo> readFromTaxFile() throws IOException {
        // For each line in the file a taxTo object is created and added as a value to the hashmap.

        String line;
        File taxFile = new File("fileData/Data/Taxes.txt");
        FileReader fr = new FileReader(taxFile);
        BufferedReader br = new BufferedReader(fr);

        br.readLine();                      // Reads (ignores) the first header line in the file.
        // Reads the remaining lines in the file
        while ((line = br.readLine()) != null) {
            // Separates the CSV file using split.
            String[] lineArr = line.split(",");

            // Converts the numerical data fields to BigDecimals.
            BigDecimal taxRate = new BigDecimal(lineArr[2]);

            // Creates a TaxTo object with the data.
            TaxTo taxTo = new TaxTo(lineArr[0], lineArr[1], taxRate);

            // Add data to the map as key:stateName with value:TaxTo.
            taxHashMap.put(lineArr[1], taxTo);
        }
        // Copy of the hashMap which will be sent between the layers.
        // EDIT: NEED TO CHECK THESE HASHMAPS DON'T REFERENCE THE SAME OBJECTS.
        Map<String, TaxTo> copyTaxHashMap = new HashMap<>(taxHashMap);
        return copyTaxHashMap;
    }

    public TaxTo fetchTaxTo(String key) {
        // Returning a copy of the taxTo object which had the key value 'key' in the taxHashMap.
        return taxHashMap.get(key).copyTaxTo();
    }
}
