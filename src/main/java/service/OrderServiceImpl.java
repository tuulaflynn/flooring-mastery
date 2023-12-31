package service;

import dao.OrderDao;
import dao.OrderDaoImpl;
import model.OrderTo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;

    public OrderServiceImpl() {
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

    public boolean isDateFuture(String userDate) {
        // Method returns true is userDate is in the future, and false otherwise.
        LocalDate todayLd = LocalDate.now();
        LocalDate orderLd;
        orderLd = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("MMddyyyy"));
        Period future = todayLd.until(orderLd);

        if (future.isNegative()) {
            return false;
        } else if (future.isZero()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addOrder(String orderDate, OrderTo orderTo) {
        return orderDao.addOrder(orderDate, orderTo);
    }

    @Override
    public int calculateOrderNumber() {
        return orderDao.calculateOrderNumber();
    }

    @Override
    public OrderTo fetchOrder(String orderDate, int orderNumber) {
        return orderDao.fetchOrder(orderDate, orderNumber);
    }


    @Override
    public String removeOrder(String orderDate, int orderNumber) {
        return orderDao.removeOrder(orderDate, orderNumber);
    }

    @Override
    public void exportToOrdersFolder() throws IOException {
        orderDao.exportToOrdersFolder();
    }
}