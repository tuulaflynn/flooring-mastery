package model;

import java.math.BigDecimal;

public class ProductTo {

    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

    public ProductTo(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    // Only need getters as ProductTo objects will be initialised by reading on the data file and not edited during the program.

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return  "productType='" + productType + '\'' +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot ;
    }

    public ProductTo copyProductTo() {
        return new ProductTo(this.productType, this.costPerSquareFoot, this.laborCostPerSquareFoot);
    }

}
