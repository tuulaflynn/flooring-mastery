package dao;

import model.OrderTo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    Map<String, List<OrderTo>> hashMapDateAndOrderCollections = new HashMap<>();


    @Override
    public Map<String, List<OrderTo>> readFromOrderFolder() throws IOException {
        // Array of all the files in Orders folder.
        File ordersFolder = new File("fileData/Orders");
        File[] files = ordersFolder.listFiles();

        // For each file read the lines in the file to a collection. Add the collection as a value to the hashMap.
        for (File file : files) {
            // The fileDate is the orderDate, it is the key to the collection of orders on a given sales date.
            String fileName = file.getName();
            String fileDate = fileName.substring(7, (fileName.length() - 4));

            // Adding the collection with the key fileDate and value, a collection containing all OrderTo objects from a file.
            hashMapDateAndOrderCollections.put(fileDate, readFromOrderFile(file));
        }
        return hashMapDateAndOrderCollections;
    }

    public List<OrderTo> readFromOrderFile(File file) throws IOException {
        // All the data from a file is added to a collection called ordersSameDateCollection.
        List<OrderTo> ordersSameDateCollection = new ArrayList<>();

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        br.readLine();       // To account for the header line in each file.

        while ((line = br.readLine()) != null)
        {
            // Reads and saves all the records from an order file into orderTo object.
            String[] lineArr = line.split(",");     // Data from a record is saved in an array as it is a CSV file.
            int orderNumber = Integer.parseInt(lineArr[0]);
            String customerName = lineArr[1];
            String state = lineArr[2];
            BigDecimal taxRate = new BigDecimal(lineArr[3]);
            String productType = lineArr[4];
            BigDecimal area = new BigDecimal(lineArr[5]);
            BigDecimal costPerSquareFoot = new BigDecimal(lineArr[6]);
            BigDecimal laborPerSquareFoot = new BigDecimal(lineArr[7]);
            BigDecimal materialCost = new BigDecimal(lineArr[8]);
            BigDecimal laborCost = new BigDecimal(lineArr[9]);
            BigDecimal tax = new BigDecimal(lineArr[10]);
            BigDecimal total = new BigDecimal(lineArr[11]);

            // Creates an orderTo object for the record.
            OrderTo orderTo = new OrderTo(orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot,
                    laborPerSquareFoot, materialCost, laborCost, tax, total);

            // Object created from the data is added to the collection.
            ordersSameDateCollection.add(orderTo);
        }
        // Returns the collection of all the records (which are now objects) from the file.
        return ordersSameDateCollection;
    }

    @Override
    public List<OrderTo> fetchOrdersForOrderDate(String userDate) {
        // Search the hashMap for the key 'userDate' and return its value (a collection). If no such key exits, null is returned.
        return hashMapDateAndOrderCollections.get(userDate);
    }

}

