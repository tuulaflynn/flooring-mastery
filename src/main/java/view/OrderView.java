package view;

import service.OrderService;
import service.OrderServiceImpl;

import java.io.IOException;

public class OrderView {

    OrderService orderService;

    public OrderView() {
        orderService = new OrderServiceImpl();
    }
    public void readFromOrderFolder() {
        try {
            System.out.println(orderService.readFromOrderFolder());
        } catch (IOException e) {
            System.out.println("There has been an error trying to read the data from Orders folder.");
        }
    }

}
