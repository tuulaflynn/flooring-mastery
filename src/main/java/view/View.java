package view;

import model.OrderTo;
import service.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class View {

    // Class variables are declared outside the constructor, so they can be used by all methods of the class.
    TaxService taxService;
    ProductService productService;
    OrderService orderService;

    public View() {
        // Constructor initialises service objects in order to send messages (method calls) to the service layer.
        taxService = new TaxServiceImpl();
        productService = new ProductServiceImpl();
        orderService = new OrderServiceImpl();

        // Constructor calls methods which read and store all folders and files containing data into collections.
        readFromProductFile();
        readFromTaxFile();
        readFromOrderFolder();
    }

    public void readFromTaxFile() {
        try {
            taxService.readFromTaxFile();
        } catch (IOException e) {
            System.out.println("There has been an error trying to read from 'Taxes.txt' file.");
        }
    }
    public void readFromProductFile() {
        try {
           productService.readFromProductFile();
        } catch (IOException e) {
            System.out.println("There has been an error trying to read from 'Products.txt' file.");
        }
    }
    public void readFromOrderFolder() {
        try {
            orderService.readFromOrderFolder();
        } catch (IOException e) {
            System.out.println("There has been an error trying to read the data from Orders folder.");
        }
    }

    public void homeScreen() {
        String userChoice = "";
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("**************************************************");
            System.out.println("* <<Flooring Program>>");
            System.out.println("* 1. Display Orders");
            System.out.println("* 2. Add an Order");
            System.out.println("* 3. Edit an Order");
            System.out.println("* 4. Remove an Order");
            System.out.println("* 5. Export All Data");
            System.out.println("* 6. Quit");
            System.out.println("**************************************************");

            System.out.println("Enter option: ");
            userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("Enter date for orders: (MMDDYYYY)");
                    String userDate = scan.nextLine();
                    // Saving the collection returned for given orderDate.
                    List<OrderTo> returnObject = orderService.fetchOrdersForOrderDate(userDate);
                    if (returnObject != null) {
                        System.out.println("The orders for date " + userDate + " are...");
                        for (OrderTo order: returnObject) {
                            System.out.println(order);
                        }
                    } else {        // No fileName with the given date exists in the hashMap.
                        System.out.println("Error. No orders exist for date " + userDate);
                    }
                    break;
                case "2":
                    System.out.println("add an order");
                    break;
                case "3":
                    System.out.println("edit an order");
                    break;
                case "4":
                    System.out.println("remove an order");
                    break;
                case "5":
                    System.out.println("export all data");
                    break;
                case "6":
                    System.out.println("Quiting program...");
                    break;
                default:
                    System.out.println("Enter a valid menu option (number only).");
            }
        } while (!userChoice.equals("6"));
    }

}
