package dao;

import model.ProductTo;

import java.util.List;

public interface ProductDao {
    List<ProductTo> readFromProductFile(); // Each object of product is a line in the file

}