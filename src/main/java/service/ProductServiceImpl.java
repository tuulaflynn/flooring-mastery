package service;

import dao.ProductDao;
import model.ProductTo;

import java.io.IOException;
import java.util.Map;

public class ProductServiceImpl implements ProductService {

    @Override
    public Map <String, ProductTo> readFromProductFile() throws IOException {
        return ProductDao.readFromProductFile();
    }

    @Override
    public String displayProductTypeAndPrice() {
        return ProductDao.displayProductTypeAndPrice();
    }

    @Override
    public ProductTo fetchProductTo(String productType){
        return ProductDao.fetchProductTo(productType);
    }
}
