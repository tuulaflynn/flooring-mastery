package dao;

import model.OrderTo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    Map<String, List<OrderTo>> readFromOrderFolder() throws IOException;

    List<OrderTo> fetchOrdersForOrderDate(String userDate);

    boolean addOrder(String orderDate, OrderTo orderTo);

    int calculateOrderNumber();

    void editOrder();

    void exportToOrdersFolder() throws IOException;

}
