package dao;

import model.OrderTo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    Map<String, List<OrderTo>> readFromOrderFolder() throws IOException;

}
