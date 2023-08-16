package service;

import dao.OrderDao;
import dao.OrderDaoImplStub;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    @Test
    public void testAddOrder() {
        // Arrange
        // Create service layer object which calls the StubOrderDaoImpl class (stud dao class).
        OrderService orderService = new OrderServiceImpl();
        OrderDao orderDaoStub = new OrderDaoImplStub();


        // Act

        // Assert
    }
}