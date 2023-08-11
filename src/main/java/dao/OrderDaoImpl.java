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
    List<OrderTo> ordersSameDateCollection = new ArrayList<>();

    public Map<String, List<OrderTo>> readFromOrderFolder() throws IOException {
        File ordersFolder = new File("fileData/Orders");
        File[] files = ordersFolder.listFiles();

        for (File file : files) {
            readFromOrderFile(file);        // Method returns a collection containing all OrderTo with the same orderDate.

            // The fileDate is the orderDate, it is the key to the collection of orders on a given sales date.
            String fileName = file.getName();
            String fileDate = fileName.substring(7, (fileName.length() - 4));
            // Adding the collection with the key value fileDate to the hashmap.
            hashMapDateAndOrderCollections.put(fileDate, ordersSameDateCollection);
        }
        return hashMapDateAndOrderCollections;
    }

    public List<OrderTo> readFromOrderFile(File file) throws IOException {
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

            OrderTo orderTo = new OrderTo(orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot,
                    laborPerSquareFoot, materialCost, laborCost, tax, total);

            // Objects from the same orderDate (file) are added to a collection.
            ordersSameDateCollection.add(orderTo);
        }
        return ordersSameDateCollection;
    }

}

