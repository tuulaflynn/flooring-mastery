package dao;

import model.OrderTo;
import model.ProductTo;
import model.TaxTo;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class OrderDaoImpl implements OrderDao {

    // HashMap collection to store all the data from Orders folder. The key is the date which is a substring of the file name.
    // The value is a collection of orderTo objects from the file which formed the key.
    private Map<String, List<OrderTo>> hashMapDateAndOrderCollections = new HashMap<>();

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

        while ((line = br.readLine()) != null) {
            // Reads and saves all the records from an order file into orderTo object.
            String[] lineArr = line.split(",");     // Data from a record is saved in an array as it is a CSV file.
            int orderNumber = Integer.parseInt(lineArr[0]);
            String customerName = lineArr[1];
            String stateAbbreviation = lineArr[2];
            TaxTo taxTo = TaxDao.fetchTaxTo(stateAbbreviation);
            String productType = lineArr[4];
            ProductTo productTo = ProductDao.fetchProductTo(productType);
            BigDecimal area = new BigDecimal(lineArr[5]);
            // Initialises an orderTo object for the record (which holds the data for a singular order)
            OrderTo orderTo = new OrderTo(orderNumber, customerName, taxTo, productTo, area);

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

    @Override
    public boolean addOrder(String orderDate, OrderTo orderTo) {
        // First method returns the value for orderDate, which is an ArrayList of orderTo object (all with the same orderDate).
        List<OrderTo> potentialCollection = hashMapDateAndOrderCollections.get(orderDate);
        if (potentialCollection != null) {
            // Add orderTo to the collection that already exists in the hashmap, i.e. there are already orders on given date.
            potentialCollection.add(orderTo);
        } else {
            // Create the collection which will store all orders for 'orderDate'.
            List<OrderTo> ordersSameDateCollection = new ArrayList<>();
            // Add the created orderTo to the collection.
            ordersSameDateCollection.add(orderTo);
            // Add the collection to the hashmap with key of the orderDate.
            hashMapDateAndOrderCollections.put(orderDate, ordersSameDateCollection);
        }
        return true;
    }

    @Override
    public int calculateOrderNumber() {
        // The hashMapDateAndOrderCollection keys need to be traversed through. Each key represents a file.
        // Each key's value is a collection, which represent all the orders of the file (all orders with the same orderDate).
        // The size of all these collections need to be added to calculate the total number of orders.
        int orderNumber = 0;

        for (List<OrderTo> OrdersSameDateCollection : hashMapDateAndOrderCollections.values()) {
            // Size of each collection is the number of orders for that date.
            orderNumber += OrdersSameDateCollection.size();

        }
        // At the end of the loop orderNumber represents the total number of orders.
        return ++orderNumber;       // Add one to orderNumber and then return it.
        // This generates an order number for the user based on the next available order #.
    }

    @Override
    public OrderTo fetchOrder(String orderDate, int orderNumber) {
        Iterator<OrderTo> iterator = hashMapDateAndOrderCollections.get(orderDate).iterator();
        while (iterator.hasNext()) {
            OrderTo orderTo = iterator.next();
            if (orderTo.getOrderNumber() == orderNumber) {
                return orderTo;
            }
        }
        return null;
    }

    @Override
    public String removeOrder(String orderDate, int orderNumber) {
        OrderTo orderToDelete = fetchOrder(orderDate, orderNumber);
        hashMapDateAndOrderCollections.get(orderDate).remove(orderToDelete);
        return "Confirmation. Order has been removed.";
    }

    @Override
    public void exportToOrdersFolder() throws IOException {
        // Each key in hashMapsDateAndOrderCollections a string date. A file will be created named Orders_stringDate.txt for each key.
        // The file will contain the records of all orders stored in the value (a collection) for the key.
        // The file will be a header line and then each order on a line.

        // Traverse through the hashMap, for each key.
        for (String dateString : hashMapDateAndOrderCollections.keySet()) {
            // Create a new file if one does not exist for the date
            String fileNameWithSubDirectory = "fileData/Orders/" + "Orders_" + dateString + ".txt";
            FileWriter fw = new FileWriter(fileNameWithSubDirectory);

            // Write the header line for the file
            fw.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n");

            // For each element in the value collection, write the line to the file.
            for (OrderTo orderTo : hashMapDateAndOrderCollections.get(dateString)) {
                fw.write(orderTo.getOrderNumber() + "," +
                        orderTo.getCustomerName() + "," +
                        orderTo.getTaxTo().getStateAbbreviation() + "," +
                        orderTo.getTaxTo().getTaxRate() + "," +
                        orderTo.getProductTo().getProductType() + "," +
                        orderTo.getArea() + "," +
                        orderTo.getProductTo().getCostPerSquareFoot() + "," +
                        orderTo.getProductTo().getLaborCostPerSquareFoot() + "," +
                        orderTo.getMaterialCost() + "," +
                        orderTo.getLaborCost() + "," +
                        orderTo.getTax() + "," +
                        orderTo.getTotal() +
                        "\n");
                fw.flush();
            }
            fw.close();
        }
    }

}

