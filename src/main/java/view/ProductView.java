package view;

import service.ProductService;
import service.ProductServiceImpl;

import java.io.IOException;

public class ProductView {
    ProductService productService;

    public ProductView() {
        productService = new ProductServiceImpl();
    }

    public void readFromProductFile() {

        try {
            System.out.println(productService.readFromProductFile());
        } catch (IOException e) {
            System.out.println("There has been an error trying to read from 'Products.txt' file.");
        }
    }
}
