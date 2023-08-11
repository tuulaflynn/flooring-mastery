package service;

import dao.OrderDao;
import dao.OrderDaoImpl;
import model.OrderTo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;

    public OrderServiceImpl(){
        orderDao = new OrderDaoImpl();
    }

    @Override
    public Map<String, List<OrderTo>> readFromOrderFolder() throws IOException {
        return orderDao.readFromOrderFolder();
    }

    @Override
    public List<OrderTo> fetchOrdersForOrderDate(String userDate) {
        return orderDao.fetchOrdersForOrderDate(userDate);
    }

}
