package dao;

import model.ProductTo;

import java.io.IOException;
import java.util.Map;

public interface ProductDao {
    Map<String, ProductTo> readFromProductFile() throws IOException; // Each object of product is initialised from a line in the file

}