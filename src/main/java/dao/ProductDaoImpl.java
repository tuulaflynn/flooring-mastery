package dao;

import model.ProductTo;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<ProductTo> readFromProductFile() {
        //for each line in the file, create an object with the product details
        // using the sample data we will have 4 objects
        // we will store these objects in productsHashmap with key: productType, value: productObject
        return null;
    }
}
