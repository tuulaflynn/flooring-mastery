package view;

import dao.ProductDao;
import dao.TaxDao;
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
    private TaxService taxService;
    private ProductService productService;
    private OrderService orderService;

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
                        for (OrderTo order : returnObject) {
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

                    System.out.println("Enter state abbreviation e.g. TX for Texas: ");
                    String userStateAbbreviation = scan.nextLine();
                    // Fetching the value, taxTo object, for the key 'userStateAbbreviation' from the taxHashMap collection.
                    TaxTo taxTo = taxService.fetchTaxTo(userStateAbbreviation);
                    // If no such key exists in the hashMap, the stateAbbreviation entered is invalid.
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
                    OrderTo orderTo = new OrderTo(orderNumber, userCustomerName, taxTo, productTo, userArea);

                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println("Summary of your order: \n");
                    System.out.println(orderTo.orderBreakdownDisplay());

                    System.out.println("Confirm order: (y/n)");
                    String confirm = scan.nextLine();
                    if (confirm.equals("y") || confirm.equals("Y")) {
                        orderService.addOrder(userOrderDate, orderTo);
                        System.out.println("Confirmation, the order has been added.");
                    } else {
                        System.out.println("Order has not been added. Returning to main menu.");
                    }
                    break;

                case "3":
                    System.out.println("Enter date for order to edit: (MMDDYYYY)");
                    String userDateEdit = scan.nextLine();

                    // Saving the collection returned for given orderDate.
                    List<OrderTo> returnObjectEdit = orderService.fetchOrdersForOrderDate(userDateEdit);

                    if (returnObjectEdit != null) {
                        // If order date exists ask for the order number to identify the order.
                        System.out.println("Enter the order number:");

                        // Checks the user entered an integer.
                        int userNumberEdit = 0;
                        try {
                            userNumberEdit = scan.nextInt();
                            scan.nextLine();        // So the scanner reads the whole line and is in the correct positions for the next scan.
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid order number. Returning to main menu.");
                            break;
                        }

                        // Boolean will be set to true if an order number is found when traversing the collection.
                        boolean validOrderNumber = false;

                        // For each order in the collection of orders with given orderDate, check if there orderNumber matches user entered order number.
                        for (OrderTo order : returnObjectEdit) {
                            // If there is a match, user has the option to edit details only for, name, state Abb., product type and area.
                            if (order.getOrderNumber() == userNumberEdit) {
                                System.out.println("Order selected...");

                                System.out.println("Enter customer name (" + order.getCustomerName() + ") :");
                                String editCustomerName = scan.nextLine();
                                if (editCustomerName.isEmpty()) {
                                    // If user enters only the enter key, the original order field remains.
                                    editCustomerName = order.getCustomerName();
                                }

                                System.out.println("Enter state (abbreviated) (" + order.getTaxTo().getStateAbbreviation() + ") :");
                                String editState = scan.nextLine();
                                TaxTo editTaxTo;
                                if (editState.isEmpty()) {
                                    editTaxTo = order.getTaxTo();
                                } else if (TaxDao.taxHashMap.containsKey(editState)) {
                                    // Error handling to check editState is a valid state we sell to.
                                    // Object taxTo is set to be updated to the associated object with state abbreviation 'editState'.
                                    editTaxTo = TaxDao.fetchTaxTo(editState);
                                } else {
                                    System.out.println("Invalid state entry. We do not sell in the state '" + editState + "'.");
                                    break;
                                    // EDIT: could add an option to see all valid states here.
                                }

                                System.out.println("Enter product type (" + order.getProductTo().getProductType() + ") :");
                                String editProductType = scan.nextLine();
                                ProductTo editProductTo;
                                if (editProductType.isEmpty()) {
                                    // Object productTo, which orderTo is composed of, remains unchanged.
                                    editProductTo = order.getProductTo();
                                } else if (ProductDao.productHashMap.containsKey(editProductType)) {
                                    // Error handling to check editProductType is a valid product type we make.
                                    // Object productTo is set to be updated to the associated productTo which has the field 'editProductType' for productType.
                                    editProductTo = ProductDao.fetchProductTo(editProductType);
                                } else {
                                    System.out.println("Invalid product type. We do not make product '" + editProductType + "'.");
                                    break;
                                    // EDIT: could add an option to see all product types here.
                                }

                                System.out.println("Enter area (" + order.getArea() + ") :");
                                String editArea = scan.nextLine();
                                BigDecimal editAreaBigDec;
                                if (editArea.isEmpty()) {
                                    editAreaBigDec = order.getArea();
                                } else {
                                    try {
                                        editAreaBigDec = new BigDecimal(editArea);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Error. Area has to be a positive decimal.");
                                        break;      // Exits loop and returns to main menu.
                                    }
                                }

                                // Creating the new edited order object.
                                OrderTo orderToEdit = new OrderTo(userNumberEdit, editCustomerName, editTaxTo,
                                        editProductTo, editAreaBigDec);

                                // Displaying the new edited order object to the user.
                                System.out.println("The edited order is...");
                                System.out.println(orderToEdit.orderBreakdownDisplay());

                                // Check the user wants to make the edit to the order.
                                System.out.println("Confirm order: (y/n)");
                                String confirmEdit = scan.nextLine();
                                if (confirmEdit.equals("y") || confirmEdit.equals("Y")) {
                                    // Delete the original order object.
                                    //orderService.deleteOrder(userDateEdit, orderToEdit);
                                    // Add the edited order object back to the collection.
                                    //orderService.addOrder(userDateEdit, orderToEdit);
                                    System.out.println("Confirmation, the order has been updated.");
                                } else {
                                    System.out.println("Order has not been added. Returning to main menu.");
                                    break;
                                }
                                validOrderNumber = true;
                                break;  // Put in for efficiency - there will be at most one match for the orderNumber, if this match is executed for loop can be exited.
                            }
                        }
                        if (!validOrderNumber) {
                            System.out.println("Error. No orders with order number " + userNumberEdit + " exist for date " + userDateEdit + ".");
                            break;
                        }
                    } else {        // No fileName with the given date exists in the hashMap.
                        System.out.println("Error. No orders exist for date " + userDateEdit);
                    }
                    break;

                case "4":
                    System.out.println("Enter the date for the order (MMDDYYYY): ");
                    // Should add proper error handling for the format of the date.
                    String removeOrderDate = scan.nextLine();

                    // Checking that the user has entered only numbers for the date.
                    try {
                        Integer.parseInt(removeOrderDate);
                    } catch (NumberFormatException e) {
                        System.out.println("Error. Only digits can be entered as a date.");
                        break;
                    }

                    System.out.println("Enter the order number of the order you want to remove: ");
                    // Checking order number entered is an integer.
                    int removeOrderNumber;
                    try {
                       removeOrderNumber = scan.nextInt();
                       scan.nextLine();     // To move the pointer to the end of the line (in correct position for the next scan).
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid order number. Returning to main menu... ");
                        break;
                    }

                    // Checking order fetched exists.
                    OrderTo fetchedOrderTo = orderService.fetchOrder(removeOrderDate, removeOrderNumber);
                    if (fetchedOrderTo != null) {
                        System.out.println("Selected order: ");
                        System.out.println(fetchedOrderTo.orderBreakdownDisplay());
                        System.out.println("Confirm order removal (y/n): ");
                        String confirmDelete = scan.nextLine();
                        if (confirmDelete.equals("y") || confirmDelete.equals("Y")) {
                            System.out.println(orderService.removeOrder(removeOrderDate, removeOrderNumber));
                        } else {
                            System.out.println("Order has not been removed. Program must be exported to save this action.");
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    } else {
                        System.out.println("No order exists with order number " + removeOrderNumber + " on date " + removeOrderDate + ".");
                        System.out.println("Returning to main menu...");
                        break;
                    }
                    break;

                case "5":
                    System.out.println("Exporting all orders to Orders folder.");
                    System.out.println("A file will be stored for each orderDate containing all orders on that day.");
                    System.out.println("The file is only visible once the program has been exited via option 6.");
                    try {
                        orderService.exportToOrdersFolder();
                    } catch (IOException e) {
                        System.out.println("Put in an error handling here for exporting to Orders/ failed.");
                    }
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
