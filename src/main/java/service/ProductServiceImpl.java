package service;

import dao.ProductDao;
import dao.ProductDaoImpl;
import model.ProductTo;

import java.io.IOException;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
    ProductDao productDao;

    public ProductServiceImpl() {
        productDao = new ProductDaoImpl();
    }

    @Override
    public Map <String, ProductTo> readFromProductFile() throws IOException {
        return productDao.readFromProductFile();
    }
}
