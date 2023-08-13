package view;

import model.OrderTo;
import model.ProductTo;
import model.TaxTo;
import service.*;

import java.io.IOException;
import java.math.BigDecimal;
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
                    System.out.println("Enter the date for the order (MMDDYYYY): ");
                    // Should add proper error handling for the format of the date.
                    String userOrderDate = scan.nextLine();
                    try {
                        Integer.parseInt(userOrderDate);
                    } catch (NumberFormatException e) {
                        System.out.println("Error. Only digits can be entered as a date.");
                        break;
                    }
                    if (!orderService.isDateFuture(userOrderDate)) {
                        // Date is not in the future
                        System.out.println("Error. Date must be in the future. ");
                        break;      // A loop should be created for chance to re-enter option.
                    }

                    int orderNumber = orderService.calculateOrderNumber();

                    System.out.println("Enter the customer name ");
                    String userCustomerName = scan.nextLine();
                    if (userCustomerName.equals("")) {
                        System.out.println("Customer name can not be blank.");
                    }
                    // EDIT: NEED TO MAKE IT ONLY [0-9][a-z][,.][A-Z]

                    System.out.println("Enter state (capitalised fullname): ");
                    String userStateName = scan.nextLine();
                    // Fetching the value, taxTo object, for the key 'userStateName' from the taxHashMap collection.
                    TaxTo taxTo = taxService.fetchTaxTo(userStateName);
                    // If no such key exists in the hashMap, the stateName entered is invalid.
                    if (taxTo == null) {
                        System.out.println("Error. We cannot sell in the state entered. ");
                        break;
                    }

                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println("Products and prices");
                    System.out.println(productService.displayProductTypeAndPrice());
                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println("Enter the product type: ");
                    String userProductType = scan.nextLine();
                    // Fetching the value, productTo object, for the key 'userProductType' from the productHashMap.
                    ProductTo productTo = productService.fetchProductTo(userProductType);
                    // If no such key exists in the hashMap, userProductType entry is invalid.
                    if (productTo == null) {
                        System.out.println("Error. We cannot sell in the state entered. ");
                        break;
                    }

                    System.out.println("Enter area: (positive decimal) ");
                    String userAreaString = scan.nextLine();

                    BigDecimal userArea;
                    try {
                        userArea = new BigDecimal(userAreaString);
                    } catch (NumberFormatException e) {
                        System.out.println("Error. Area entered must be only digits.");
                        break;
                    }

                    // Creating an OrderTo object with composition of objects taxTo and productTo.
                    // NEED TO WORK OUT HOW TO DO ORDERNUMBER
                    OrderTo orderTo = new OrderTo(orderNumber, userCustomerName, taxTo, productTo, userArea);

                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println("Summary of your order: \n");
                    System.out.println(orderTo.orderBreakdownDisplay());

                    System.out.println("Confirm order: (y/n)");
                    String confirm = scan.nextLine();
                    if (confirm.equals("y")) {
                        orderService.addOrder(userOrderDate, orderTo);
                        System.out.println("Confirmation, the order has been added.");
                    }
                    else {
                        System.out.println("Order has not been added. Returning to main menu.");
                    }
                    break;

                case "3":
                    System.out.println("edit an order");
                    break;
                case "4":
                    System.out.println("remove an order");
                    break;
                case "5":
                    System.out.println("Exporting all data");
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
