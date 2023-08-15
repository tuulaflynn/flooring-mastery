package service;

import model.OrderTo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, List<OrderTo>> readFromOrderFolder() throws IOException;

    List<OrderTo> fetchOrdersForOrderDate(String userDate);

    boolean isDateFuture(String userDate);

    boolean addOrder(String orderDate, OrderTo orderTo);

    int calculateOrderNumber();

    OrderTo fetchOrder(String orderDate, int orderNumber);

    String removeOrder(String orderDate, int orderNumber);

    void exportToOrdersFolder() throws IOException;

}