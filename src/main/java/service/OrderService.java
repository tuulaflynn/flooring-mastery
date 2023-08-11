package service;

import model.OrderTo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, List<OrderTo>> readFromOrderFolder() throws IOException;

}