import view.OrderView;
import view.ProductView;
import view.TaxView;

public class UserIO {
    public static void main(String[] args) {
        ProductView productView = new ProductView();
        TaxView taxView = new TaxView();
        OrderView orderView = new OrderView();

        productView.readFromProductFile();
        taxView.readFromTaxFile();
        orderView.readFromOrderFolder();
    }
}
