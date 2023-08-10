package dao;

import model.ProductTo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductDaoImpl implements ProductDao {

    Map<String, ProductTo> productsHashMap;

    public ProductDaoImpl() {
        productsHashMap = new HashMap<>();
    }

    @Override
    public Map<String, ProductTo> readFromProductFile() throws IOException {
        // For each line in the file a productTo object is created and added as a value to the hashmap.

        String line;

        FileReader fr = new FileReader("fileData/Data/Products.txt");
        BufferedReader br = new BufferedReader(fr);

        br.readLine();                      // Reads (ignores) the first header line in the file.
        // Reads the remaining lines in the file.
        while ((line = br.readLine()) != null) {
            // Separates the CSV file using split.
            String[] lineArr = line.split(",");

            // Converts the numerical data fields to BigDecimals.
            BigDecimal costPerSquareFoot = new BigDecimal(lineArr[1]);
            BigDecimal laborPerSquareFoot = new BigDecimal(lineArr[2]);

            // Create a ProductTo object with the data.
            ProductTo productTo = new ProductTo(lineArr[0], costPerSquareFoot, laborPerSquareFoot);

            // Add data to the map as key:productType with value:productIO.
            productsHashMap.put(lineArr[0], productTo);
        }

        return productsHashMap;
    }

}
