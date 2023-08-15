package dao;

import model.ProductTo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface ProductDao {
    Map<String, ProductTo> productHashMap = new HashMap<>();

    static Map<String, ProductTo> readFromProductFile() throws IOException {
        // For each line in the file a productTo object is created and added as a value to the hashmap.

        String line;
        File productsFile = new File("fileData/Data/Products.txt");
        FileReader fr = new FileReader(productsFile);
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
            productHashMap.put(lineArr[0], productTo);
        }
        // As this collection is not being modified, a copy is not necessary.
        return productHashMap;
    }

    static String displayProductTypeAndPrice() {
        Collection<ProductTo> productTos = productHashMap.values();
        String returnString = "";
        for (ProductTo productTo: productTos) {
            returnString += "product type: " + productTo.getProductType() + " || cost per sq.ft £" + productTo.getCostPerSquareFoot()
                    + " || labor per sq.ft £" + productTo.getLaborCostPerSquareFoot() + "\n";
        }
        return returnString;
    }

    static ProductTo fetchProductTo(String productType) {
        // Returning a copy of the productTo object which had the key value 'productType' in the productsHashMap.
        return productHashMap.get(productType).copyProductTo();
    }
}