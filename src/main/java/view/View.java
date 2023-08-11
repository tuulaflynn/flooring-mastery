package view;

import service.*;

import java.io.IOException;

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
            System.out.println(taxService.readFromTaxFile());
        } catch (IOException e) {
            System.out.println("There has been an error trying to read from 'Taxes.txt' file.");
        }
    }

    public void readFromProductFile() {
        try {
            System.out.println(productService.readFromProductFile());
        } catch (IOException e) {
            System.out.println("There has been an error trying to read from 'Products.txt' file.");
        }
    }

    public void readFromOrderFolder() {
        try {
            System.out.println(orderService.readFromOrderFolder());
        } catch (IOException e) {
            System.out.println("There has been an error trying to read the data from Orders folder.");
        }
    }

}
