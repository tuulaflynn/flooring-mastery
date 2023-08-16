package dao;

import model.OrderTo;
import model.ProductTo;
import model.TaxTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoImplTest {

    @Test
    public void testAddOrder() {
        // Not sure if this test is created properly.

        OrderDao orderDao = new OrderDaoImpl();

        // Arrange
        Map<String, List<OrderTo>> actualHashMapDateAndOrderCollections = new HashMap<>();
        // Creating orderTo from the record
        ProductTo productTo = new ProductTo("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
        // It is more stable to create a BigDecimal object from a string than a float/double due to precision and rounding losses.
        TaxTo taxTo = new TaxTo("CA", "California", new BigDecimal("25.00"));
        OrderTo orderTo1 = new OrderTo(1, "Ada Lovelace", taxTo, productTo, new BigDecimal("249.00"));


        // Act

        // Actual
        orderDao.addOrder("06012013", orderTo1);        // This return only true or false.
        // How can I access the size of the collection inside  the OrderDaoImpl file?

        // Expected
        List<OrderTo> collection06012013 = new ArrayList<OrderTo>();
        // Adding object to the collection
        collection06012013.add(orderTo1);
        // Adding collection to with key of the orderDate to the hashMap.
        actualHashMapDateAndOrderCollections.put("06012023", collection06012013);

        // Action
        Assertions.assertEquals(actualHashMapDateAndOrderCollections.size(), 1);    // Need to add the actual result here.

    }
}
