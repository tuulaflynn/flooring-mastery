package service;

import model.ProductTo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Map<String, ProductTo> readFromProductFile() throws IOException;

}
