package dao;

import model.ProductTo;
import model.TaxTo;

import java.io.BufferedReader;
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

        FileReader fr = new FileReader("fileData/Data/Taxes.txt");
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

            // Add data to the map as key:stateAbbreviation with value:TaxTo.
            taxHashMap.put(lineArr[0], taxTo);
        }

        return taxHashMap;
    }
}
